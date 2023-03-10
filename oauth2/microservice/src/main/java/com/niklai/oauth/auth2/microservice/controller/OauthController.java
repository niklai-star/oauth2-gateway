package com.niklai.oauth.auth2.microservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.niklai.oauth.common.Cache;
import com.niklai.oauth.auth2.microservice.request.token.AccessTokenRequest;
import com.niklai.oauth.auth2.microservice.exception.ApiException;
import com.niklai.oauth.auth2.microservice.request.login.LoginWithOauth;
import com.niklai.oauth.auth2.microservice.request.token.RefreshTokenRequest;
import com.niklai.oauth.auth2.microservice.response.ApiResult;
import com.niklai.oauth.auth2.microservice.response.TokenInfo;
import com.niklai.oauth.oauth2.domain.entity.Account;
import com.niklai.oauth.oauth2.domain.entity.Code;
import com.niklai.oauth.oauth2.domain.entity.Token;
import com.niklai.oauth.oauth2.domain.service.AccountService;
import com.niklai.oauth.oauth2.domain.service.AppService;
import com.niklai.oauth.oauth2.domain.service.CodeService;
import com.niklai.oauth.oauth2.domain.service.TokenService;
import com.niklai.oauth.oauth2.domain.utils.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
public class OauthController {

    @Autowired
    private CodeService codeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppService appService;

    @Autowired
    private Cache<String, Object> cache;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> login(@Valid @RequestBody LoginWithOauth loginReqBody) {
        // ??????App
        return appService.getByAppKey(loginReqBody.getClientId()).flatMap(app -> {
            if (app == null) {
                return Mono.error(ApiException.builder().code("500").msg("App?????????").build());
            }

            return accountService.login(loginReqBody.getAccount(), loginReqBody.getPassword()).flatMap(account -> {
                if (account == null) {
                    return Mono.error(ApiException.builder().code("500").msg("????????????????????????").build());
                }

                Code code = codeService.createNewCode(app.getAppKey(), account.getId());
                code.putCache(cache);

                if (!Objects.isNull(loginReqBody.getIsRedirect()) && !loginReqBody.getIsRedirect()) {
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("code", code.getCode());
                    if (StringUtils.isNotBlank(loginReqBody.getState())) {
                        body.put("state", loginReqBody.getState());
                    }

                    return Mono.just(ResponseEntity.ok().body(new ApiResult<HashMap>().setData(body)));
                }

                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(app.getRedirectUrl()).queryParam("code", code.getCode());
                if (!Objects.isNull(loginReqBody.getState())) {
                    uriBuilder = uriBuilder.queryParam("state", loginReqBody.getState());
                }

                return Mono.just(ResponseEntity.status(HttpStatus.FOUND).header("Location", uriBuilder.toUriString()).build());
            });
        });
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> token(@Valid @RequestBody AccessTokenRequest atReq) {
        Optional<Code> codeOptional = null;
        try {
            codeOptional = codeService.getCode(atReq.getCode());
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        if (codeOptional.isEmpty()) {
            return Mono.error(ApiException.builder().code("500").msg("code?????????").build());
        }

        Code code = codeOptional.get();
        if (!code.getAppKey().equals(atReq.getClientId())) {
            return Mono.error(ApiException.builder().code("500").msg("?????????code").build());
        }

        // ??????App
        return appService.getByAppKey(atReq.getClientId()).flatMap(app -> {
            if (app == null || !app.getAppSecret().equals(atReq.getClientSecret()) || !app.getRedirectUrl().equals(atReq.getRedirectUrl())) {
                return Mono.error(ApiException.builder().code("500").msg("App??????????????????").build());
            }

            Token token = tokenService.createNewToken(code.getAppKey(), code.getAccountId());
            token.putCache(cache);

            code.deleteCache(cache);
            return Mono.just(ResponseEntity.ok().body(new ApiResult<TokenInfo>().setData(
                    new TokenInfo()
                            .setAccessToken(token.getAccessToken())
                            .setRefreshToken(token.getRefreshToken())
                            .setExpiresIn(CacheUtils.ACCESS_TOKEN_EXPIRE.getSeconds())
                            .setRefreshExpiresIn(CacheUtils.REFRESH_TOKEN_EXPIRE.getSeconds())
            )));
        });
    }

    @PostMapping(value = "/refreshToken", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> refreshToken(@Valid @RequestBody RefreshTokenRequest reReq) {
        // ??????refreshToken????????????
        Optional<Token> tokenOptional = null;
        try {
            tokenOptional = tokenService.getByRefreshToken(reReq.getRefreshToken());
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        if (tokenOptional.isEmpty()) {
            return Mono.error(ApiException.builder().code("500").msg("Refresh Token?????????").build());
        }

        Token oldToken = tokenOptional.get();
        // ??????refreshToken??????app????????????
        return appService.getByAppKey(oldToken.getAppKey()).flatMap(app -> {
            if (app == null) {
                return Mono.error(ApiException.builder().code("500").msg("App??????????????????").build());
            }

            // ??????refreshToken????????????????????????
            return accountService.getById(oldToken.getAccountId()).flatMap(account -> {
                if (account == null) {
                    return Mono.error(ApiException.builder().code("500").msg("???????????????????????????").build());
                }

                // ??????token????????????token
                oldToken.deleteCache(cache);
                Token newToken = tokenService.createNewToken(oldToken.getAppKey(), oldToken.getAccountId());
                newToken.putCache(cache);

                return Mono.just(ResponseEntity.ok().body(new ApiResult<TokenInfo>().setData(
                        new TokenInfo()
                                .setAccessToken(newToken.getAccessToken())
                                .setRefreshToken(newToken.getRefreshToken())
                                .setExpiresIn(CacheUtils.ACCESS_TOKEN_EXPIRE.getSeconds())
                                .setRefreshExpiresIn(CacheUtils.REFRESH_TOKEN_EXPIRE.getSeconds())
                )));
            });
        });
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> logout(@Valid @NotEmpty @RequestHeader("Access-Token") String accessToken) {
        Optional<Token> tokenOptional = Optional.empty();
        try {
            tokenOptional = tokenService.getByAccessToken(accessToken);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        tokenOptional.ifPresent(token -> token.deleteCache(cache));
        return Mono.just(ResponseEntity.ok().body(new ApiResult<Boolean>().setData(true)));
    }

    @GetMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> account(@Valid @NotEmpty @RequestHeader("Access-Token") String accessToken) {
        Optional<Token> tokenOptional = Optional.empty();
        try {
            tokenOptional = tokenService.getByAccessToken(accessToken);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return Mono.error(ApiException.builder().code("500").msg("?????????Access Token").build());
        }

        return tokenOptional.<Mono<ResponseEntity>>map(token ->
                        accountService.getById(token.getAccountId()).flatMap(account -> {
                            if (account == null) {
                                return Mono.error(ApiException.builder().code("500").msg("???????????????").build());
                            }

                            return Mono.just(ResponseEntity.ok().body(new ApiResult<Account>().setData(account)));
                        }))
                .orElseGet(() ->
                        Mono.error(ApiException.builder().code("500").msg("Access Token?????????").build()));

    }
}

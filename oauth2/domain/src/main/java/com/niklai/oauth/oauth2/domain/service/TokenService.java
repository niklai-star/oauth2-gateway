package com.niklai.oauth.oauth2.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.niklai.oauth.common.Cache;
import com.niklai.oauth.oauth2.domain.utils.CacheUtils;
import com.niklai.oauth.oauth2.domain.entity.Token;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class TokenService {

    private Cache<String, Object> cache;


    public Token createNewToken(String appKey, Long accountId) {
        return new Token(
                RandomStringUtils.randomAlphanumeric(56),
                RandomStringUtils.randomAlphanumeric(56),
                appKey,
                accountId
        );
    }

    public Optional<Token> getByAccessToken(String accessToken) throws JsonProcessingException {
        Token token = (Token) cache.get(CacheUtils.accessTokenKey(accessToken));
        if (Objects.isNull(token)) {
            return Optional.empty();
        }

        return Optional.of(token);
    }

    public Optional<Token> getByRefreshToken(String refreshToken) throws JsonProcessingException {
        Token token = (Token) cache.get(CacheUtils.refreshTokenKey(refreshToken));
        if (Objects.isNull(token)) {
            return Optional.empty();
        }

        return Optional.of(token);
    }
}

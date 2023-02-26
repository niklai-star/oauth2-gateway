package com.niklai.oauth.auth2.microservice.config;

import com.niklai.oauth.common.Cache;
import com.niklai.oauth.oauth2.domain.service.AccountService;
import com.niklai.oauth.oauth2.domain.service.AppService;
import com.niklai.oauth.oauth2.domain.service.CodeService;
import com.niklai.oauth.oauth2.domain.service.TokenService;
import com.niklai.oauth.oauth2.infrastructure.repository.AccountInfoRepository;
import com.niklai.oauth.oauth2.infrastructure.repository.AppInfoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OauthDomainConfig {

    @Bean
    public AccountService accountService(AccountInfoRepository accountInfoRepository) {
        return new AccountService(accountInfoRepository);
    }
    @Bean
    public AppService appService(AppInfoRepository appInfoRepository) {
        return new AppService(appInfoRepository);
    }

    @Bean
    public CodeService codeService(Cache<String, Object> cache) {
        return new CodeService(cache);
    }

    @Bean
    public TokenService tokenService(Cache<String, Object> cache) {
        return new TokenService(cache);
    }
}

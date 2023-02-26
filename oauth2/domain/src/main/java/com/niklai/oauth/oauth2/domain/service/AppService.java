package com.niklai.oauth.oauth2.domain.service;

import com.niklai.oauth.oauth2.domain.entity.App;
import com.niklai.oauth.oauth2.domain.mapper.EntityMapper;
import com.niklai.oauth.oauth2.infrastructure.entity.AppInfo;
import com.niklai.oauth.oauth2.infrastructure.repository.AppInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class AppService {
    private AppInfoRepository appInfoRepository;

    public Mono<App> getByAppKey(String appKey) {
        return appInfoRepository.findOne(Example.of(new AppInfo().setAppKey(appKey))).map(appInfo ->
                appInfo == null ? null : EntityMapper.INSTANCE.toApp(appInfo));
    }
}

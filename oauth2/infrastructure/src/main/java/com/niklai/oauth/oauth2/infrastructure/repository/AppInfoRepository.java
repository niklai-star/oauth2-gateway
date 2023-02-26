package com.niklai.oauth.oauth2.infrastructure.repository;

import com.niklai.oauth.oauth2.infrastructure.entity.AppInfo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoRepository extends R2dbcRepository<AppInfo, Long> {
}

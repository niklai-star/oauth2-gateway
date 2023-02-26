package com.niklai.oauth.oauth2.domain.mapper;

import com.niklai.oauth.oauth2.domain.entity.Account;
import com.niklai.oauth.oauth2.domain.entity.App;
import com.niklai.oauth.oauth2.infrastructure.entity.AccountInfo;
import com.niklai.oauth.oauth2.infrastructure.entity.AppInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    Account toAccount(AccountInfo accountInfo);

    App toApp(AppInfo appInfo);
}

package com.niklai.oauth.oauth2.infrastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Accessors(chain = true)
@Table(name = "app_info")
public class AppInfo extends BaseEntity {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("app_key")
    private String appKey;

    @Column("app_secret")
    private String appSecret;

    @Column("redirect_url")
    private String redirectUrl;
}

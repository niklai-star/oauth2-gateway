package com.niklai.oauth.oauth2.infrastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Accessors(chain = true)
@Table(name = "account_info")
public class AccountInfo extends BaseEntity {

    @Id
    private Long id;

    @Column("account")
    private String account;

    @Column("password")
    private String password;
}

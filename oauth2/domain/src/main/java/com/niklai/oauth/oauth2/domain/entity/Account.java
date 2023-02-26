package com.niklai.oauth.oauth2.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Account {
    private Long id;

    private String account;
}

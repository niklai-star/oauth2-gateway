package com.niklai.oauth.oauth2.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

@Data
@Accessors(chain = true)
public class App {
    private Long id;

    private String name;

    private String appKey;

    private String appSecret;

    private String redirectUrl;

    public boolean checkAppSecret(String secret) {
        if (StringUtils.isEmpty(secret)) {
            return false;
        }

        return this.appSecret.equals(secret);
    }

    public boolean checkRedirectUrl(String redirectUrl) {
        if (StringUtils.isEmpty(redirectUrl)) {
            return false;
        }

        return this.redirectUrl.equals(redirectUrl);
    }
}

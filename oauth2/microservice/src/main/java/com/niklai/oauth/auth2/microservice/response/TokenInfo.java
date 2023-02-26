package com.niklai.oauth.auth2.microservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenInfo {

    @JsonProperty("access_type")
    private String type = "Bearer";

    @JsonProperty("access_token")
    private String AccessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("re_expires_in")
    private Long refreshExpiresIn;
}

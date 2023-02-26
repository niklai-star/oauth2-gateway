package com.niklai.oauth.auth2.microservice.request.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenRequest {
    @NotEmpty
    private String clientId;

    @NotEmpty
    private String clientSecret;

    @NotEmpty
    @Pattern(regexp = "authorization_code")
    private String grantType;

    @NotEmpty
    private String code;

    @NotEmpty
    private String redirectUrl;
}

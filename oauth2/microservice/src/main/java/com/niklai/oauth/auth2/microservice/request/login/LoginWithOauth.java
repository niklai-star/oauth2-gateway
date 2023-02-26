package com.niklai.oauth.auth2.microservice.request.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginWithOauth {

    @NotEmpty(message = "账号不能为空")
    private String account;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "ClientId不能为空")
    private String clientId;

    @NotEmpty(message = "redirectUrl不能为空")
    private String redirectUrl;

    private Boolean isRedirect;

    private String state;
}

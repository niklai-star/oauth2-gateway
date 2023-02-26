package com.niklai.oauth.auth2.microservice.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiResult<T> {
    private String code;
    private String msg;

    private T data;
}

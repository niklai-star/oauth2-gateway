package com.niklai.oauth.auth2.microservice.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiException extends RuntimeException {
    private String code;

    private String msg;

    public ApiException(String code) {
        this.code = code;
    }

    public ApiException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }
}

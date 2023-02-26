package com.niklai.oauth.auth2.microservice.exception;

import com.niklai.oauth.auth2.microservice.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResult> handlerApiException(ApiException exception) {
        log.error("ApiException", exception.getCause());
        return ResponseEntity.ok().body(new ApiResult()
                .setCode(exception.getCode())
                .setMsg(exception.getMsg())
        );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiResult> handlerBindException(WebExchangeBindException exception) {
        log.error("WebExchangeBindException", exception);
        return ResponseEntity.ok().body(new ApiResult()
                .setCode(String.valueOf(exception.getStatus().value()))
                .setMsg("参数错误")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult> handlerDefault(Exception exception) {
        log.error("Default", exception);
        return ResponseEntity.ok().body(new ApiResult()
                .setCode("500")
                .setMsg(exception.getMessage())
        );
    }
}

package com.niklai.oauth.oauth2.domain.utils;

import java.time.Duration;
import java.util.Locale;

public class CacheUtils {
    private static final String CODE_KEY_PREFIX = "code:";
    public static final Duration CODE_EXPIRE = Duration.ofMinutes(30);

    private static final String ACCESS_TOKEN_KEY_PREFIX = "access_token:";
    public static final Duration ACCESS_TOKEN_EXPIRE = Duration.ofMinutes(60);

    private static final String REFRESH_TOKEN_KEY_PREFIX = "refresh_token:";
    public static final Duration REFRESH_TOKEN_EXPIRE = Duration.ofDays(90);

    public static final String codeKey(String code) {
        return String.format(Locale.ENGLISH, "%s%s", CODE_KEY_PREFIX, code);
    }

    public static final String accessTokenKey(String accessToken) {
        return String.format(Locale.ENGLISH, "%s%s", ACCESS_TOKEN_KEY_PREFIX, accessToken);
    }

    public static final String refreshTokenKey(String refreshToken) {
        return String.format(Locale.ENGLISH, "%s%s", REFRESH_TOKEN_KEY_PREFIX, refreshToken);
    }
}

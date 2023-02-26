package com.niklai.oauth.oauth2.domain.entity;

import com.niklai.oauth.common.Cache;
import com.niklai.oauth.oauth2.domain.utils.CacheUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private String accessToken;

    private String refreshToken;

    private String appKey;

    private Long accountId;

    public void putCache(Cache<String, Object> cache) {
        cache.set(CacheUtils.accessTokenKey(this.accessToken), this, CacheUtils.ACCESS_TOKEN_EXPIRE);
        cache.set(CacheUtils.refreshTokenKey(this.refreshToken), this, CacheUtils.REFRESH_TOKEN_EXPIRE);
    }

    public void deleteCache(Cache<String, Object> cache) {
        cache.delete(CacheUtils.accessTokenKey(this.accessToken));
        cache.delete(CacheUtils.refreshTokenKey(this.refreshToken));
    }
}

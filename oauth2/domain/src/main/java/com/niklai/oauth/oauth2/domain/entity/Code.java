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
public class Code {

    private String code;

    private Long accountId;

    private String appKey;

    public void putCache(Cache<String, Object> cache) {
        cache.set(CacheUtils.codeKey(this.code), this, CacheUtils.CODE_EXPIRE);
    }

    public void deleteCache(Cache<String, Object> cache) {
        cache.delete(CacheUtils.codeKey(this.code));
    }
}

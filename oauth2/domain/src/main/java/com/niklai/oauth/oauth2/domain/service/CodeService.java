package com.niklai.oauth.oauth2.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.niklai.oauth.common.Cache;
import com.niklai.oauth.oauth2.domain.utils.CacheUtils;
import com.niklai.oauth.oauth2.domain.entity.Code;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class CodeService {

    private Cache<String, Object> cache;

    public Code createNewCode(String appKey, Long userId) {
        return new Code(RandomStringUtils.randomAlphanumeric(8), userId, appKey);
    }

    public Optional<Code> getCode(String code) throws JsonProcessingException {
        Code c = (Code) cache.get(CacheUtils.codeKey(code));
        if (Objects.isNull(c)) {
            return Optional.empty();
        }

        return Optional.of(c);
    }
}

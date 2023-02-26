package com.niklai.oauth.common.cache;

import com.niklai.oauth.common.Cache;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@AllArgsConstructor
public class CacheImpl implements Cache<String, Object> {
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}

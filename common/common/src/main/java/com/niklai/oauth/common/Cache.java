package com.niklai.oauth.common;

import java.time.Duration;

public interface Cache<K, V> {
    void set(K key, V value, Duration duration);

    void delete(K key);

    V get(K key);
}

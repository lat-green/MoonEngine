package com.greentree.engine.moon.assets.serializator.manager.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class HashMapCache<K, T> implements Cache<K, T> {

    private final Map<K, T> cache = new HashMap<>();

    public synchronized boolean has(K key) {
        return cache.containsKey(key);
    }

    public synchronized T get(K key) {
        if (cache.containsKey(key))
            return cache.get(key);
        return null;
    }

    public synchronized T set(K key, Supplier<T> create) {
        if (cache.containsKey(key))
            return cache.get(key);
        var value = create.get();
        cache.put(key, value);
        return value;
    }

}

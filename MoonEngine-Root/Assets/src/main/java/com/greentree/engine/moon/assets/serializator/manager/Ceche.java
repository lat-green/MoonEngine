package com.greentree.engine.moon.assets.serializator.manager;

import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

import static org.apache.logging.log4j.LogManager.getLogger;

@Deprecated
public final class Ceche<K, T> {

    private static final Logger LOG = getLogger(Ceche.class);
    private final Map<K, T> cache = new WeakHashMap<>();

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

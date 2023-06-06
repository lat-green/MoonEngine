package com.greentree.engine.moon.assets.serializator.manager;

import java.util.function.Supplier;

public interface Cache<K, T> {

    default boolean has(K key) {
        return get(key) != null;
    }

    T get(K key);

    T set(K key, Supplier<T> create);

}

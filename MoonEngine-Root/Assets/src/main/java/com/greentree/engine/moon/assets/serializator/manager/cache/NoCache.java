package com.greentree.engine.moon.assets.serializator.manager.cache;

import java.util.function.Supplier;

public class NoCache<K, R> implements Cache<K, R> {

    @Override
    public R get(K key) {
        return null;
    }

    @Override
    public R set(K key, Supplier<R> create) {
        return create.get();
    }

}

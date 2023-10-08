package com.greentree.engine.moon.assets.serializator.manager.cache;

import kotlin.jvm.functions.Function0;

public class NoCache<K, R> implements Cache<K, R> {

    @Override
    public R get(K key) {
        return null;
    }

    @Override
    public R set(K key, Function0<? extends R> create) {
        return create.invoke();
    }

}

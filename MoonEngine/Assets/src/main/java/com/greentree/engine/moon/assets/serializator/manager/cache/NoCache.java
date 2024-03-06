package com.greentree.engine.moon.assets.serializator.manager.cache;

import kotlin.jvm.functions.Function0;

public class NoCache<K, R> implements Cache<K, R> {

    @Override
    public R get(K key) {
        return null;
    }

    @Override
    public <R1 extends R> R1 set(K key, Function0<? extends R1> create) {
        return create.invoke();
    }

}

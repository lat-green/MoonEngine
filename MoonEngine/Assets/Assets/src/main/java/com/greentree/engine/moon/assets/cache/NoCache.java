package com.greentree.engine.moon.assets.cache;

import kotlin.jvm.functions.Function0;

public final class NoCache<K, R> implements Cache<K, R> {

    private static final NoCache INSTANCE = new NoCache();

    private NoCache() {
    }

    public static <K, R> NoCache<K, R> instance() {
        return (NoCache<K, R>) INSTANCE;
    }

    @Override
    public R get(K key) {
        return null;
    }

    @Override
    public <R1 extends R> R1 set(K key, Function0<? extends R1> create) {
        return create.invoke();
    }

}

package com.greentree.engine.moon.assets.value.provider;

import java.io.ObjectStreamException;

public final class NullProvider<T> implements ValueProvider<T> {

    public static final int CHARACTERISTICS = CONST | DISTINCT_CHANGE;
    private static final NullProvider<?> INSTANCE = new NullProvider<>();

    private NullProvider() {
    }

    @SuppressWarnings("unchecked")
    public static <T> NullProvider<T> instance() {
        return (NullProvider<T>) INSTANCE;
    }

    @Override
    public int characteristics() {
        return CHARACTERISTICS;
    }

    @Override
    public T get() {
        return null;
    }

    @Override
    public ValueProvider<T> copy() {
        return this;
    }

    @Override
    public boolean isChenge() {
        return false;
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }

}

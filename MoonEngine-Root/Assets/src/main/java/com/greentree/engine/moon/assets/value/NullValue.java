package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.provider.NullProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

import java.io.ObjectStreamException;

public final class NullValue<T> implements Value<T> {

    private static final long serialVersionUID = 1L;

    private static final NullValue<?> INSTANCE = new NullValue<>();

    private NullValue() {
    }

    @SuppressWarnings("unchecked")
    public static <T> NullValue<T> instance() {
        return (NullValue<T>) INSTANCE;
    }

    @Override
    public String toString() {
        return "NULL";
    }

    @Override
    public int characteristics() {
        return NullProvider.CHARACTERISTICS;
    }

    @Override
    public T get() {
        return null;
    }

    @Override
    public ValueProvider<T> openProvider() {
        return NullProvider.instance();
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }

}

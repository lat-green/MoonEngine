package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.provider.ReduceProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

import java.util.Objects;

public final class ReduceValue<T> implements Value<T> {

    private static final long serialVersionUID = 1L;

    private final MutableValue<Value<T>> result = new MutableValue<>();

    public ReduceValue(Value<T> Value) {
        set(Value);
    }

    public void set(Value<T> Value) {
        Objects.requireNonNull(Value);
        result.set(Value);
    }

    @Override
    public int characteristics() {
        return ReduceProvider.CHARACTERISTICS;
    }

    @Override
    public ValueProvider<T> openProvider() {
        return ReduceProvider.newProvider(result);
    }

    @Override
    public String toString() {
        return "ReduceValue [" + result + "]";
    }

}

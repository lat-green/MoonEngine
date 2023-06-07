package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.provider.ConstProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

import java.util.Objects;

public final class ConstValue<T> implements Value<T> {

    private static final long serialVersionUID = 1L;

    private final T value;

    private ConstValue(T value) {
        this.value = value;
    }

    public static <T> Value<T> newValue(T value) {
        if (value == null)
            return NullValue.instance();
        return new ConstValue<>(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstValue<?> that = (ConstValue<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return "Const [" + value + "]";
    }

    @Override
    public ValueProvider<T> openProvider() {
        return ConstProvider.newValue(value);
    }

    @Override
    public int characteristics() {
        return ConstProvider.CHARACTERISTICS;
    }

}

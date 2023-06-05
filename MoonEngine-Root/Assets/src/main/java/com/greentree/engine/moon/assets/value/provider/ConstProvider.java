package com.greentree.engine.moon.assets.value.provider;

public final class ConstProvider<T> implements ValueProvider<T> {

    public static final int CHARACTERISTICS = CONST | NOT_NULL | DISTINCT_CHANGE | CECHED;

    private final T value;

    private ConstProvider(T value) {
        this.value = value;
    }

    public static <T> ValueProvider<T> newValue(T value) {
        if (value == null)
            return NullProvider.instance();
        return new ConstProvider<>(value);
    }

    @Override
    public int characteristics() {
        return CHARACTERISTICS;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public ValueProvider<T> copy() {
        return this;
    }

    @Override
    public boolean isChenge() {
        return false;
    }

    @Override
    public String toString() {
        return "ConstProvider [" + value + "]";
    }

}

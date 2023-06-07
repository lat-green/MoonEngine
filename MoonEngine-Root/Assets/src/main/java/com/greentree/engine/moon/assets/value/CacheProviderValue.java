package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.provider.ValueProvider;

import java.util.function.Consumer;

public final class CacheProviderValue<T> implements Value<T> {

    private static final long serialVersionUID = 1L;

    private final ValueProvider<T> provider;

    private long lastUpdate = Long.MIN_VALUE;

    private CacheProviderValue(ValueProvider<T> provider) {
        this.provider = provider;
    }

    @Override
    public ValueProvider<T> openProvider() {
        return new Provider();
    }

    @Override
    public int characteristics() {
        return provider.characteristics() | ONE_PROVIDER;
    }

    public static <T> Value<T> newValue(Value<T> value) {
        if (value.hasCharacteristics(CONST) || value.hasCharacteristics(ONE_PROVIDER))
            return value;
        if (value instanceof CacheProviderValue result)
            return result;
        return newValue(value.openProvider());
    }

    public static <T> Value<T> newValue(ValueProvider<T> provider) {
        return new CacheProviderValue<>(provider);
    }

    private class Provider implements ValueProvider<T> {

        private long localLastUpdate = lastUpdate;

        @Override
        public int characteristics() {
            return provider.characteristics() | ONE_PROVIDER;
        }

        @Override
        public T get() {
            if (provider.isChenge())
                lastUpdate++;
            localLastUpdate = lastUpdate;
            return provider.get();
        }

        @Override
        public boolean tryGet(Consumer<? super T> action) {
            return provider.tryGet(x -> {
                lastUpdate++;
                localLastUpdate = lastUpdate;
                action.accept(x);
            });
        }

        @Override
        public boolean isChenge() {
            if (provider.isChenge())
                lastUpdate++;
            return localLastUpdate < lastUpdate;
        }

        @Override
        public T getNotChenge() {
            return provider.getNotChenge();
        }

    }

}

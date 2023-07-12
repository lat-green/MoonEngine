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
        return new Provider(this);
    }

    @Override
    public int characteristics() {
        return provider.characteristics() | ONE_PROVIDER;
    }

    public static <T> Value<T> newValue(Value<T> value) {
        if (value.hasCharacteristics(CONST) || value.hasCharacteristics(ONE_PROVIDER))
            return value;
        return newValue(value.openProvider());
    }

    public static <T> Value<T> newValue(ValueProvider<T> provider) {
        if (provider instanceof Provider<T> p)
            return p.value;
        return new CacheProviderValue<>(provider);
    }

    @Override
    public String toString() {
        return "CacheProviderValue[" + provider + ']';
    }

    private static final class Provider<T> implements ValueProvider<T> {

        private final CacheProviderValue<T> value;
        private long localLastUpdate;

        private Provider(CacheProviderValue<T> value) {
            this.value = value;
            localLastUpdate = value.lastUpdate;
        }

        @Override
        public String toString() {
            return "Provider of " + value;
        }

        @Override
        public int characteristics() {
            return value.provider.characteristics() | ONE_PROVIDER;
        }

        @Override
        public T get() {
            if (value.provider.isChenge())
                value.lastUpdate++;
            localLastUpdate = value.lastUpdate;
            return value.provider.get();
        }

        @Override
        public T getNotChenge() {
            return value.provider.getNotChenge();
        }

        @Override
        public boolean tryGet(Consumer<? super T> action) {
            return value.provider.tryGet(x -> {
                value.lastUpdate++;
                localLastUpdate = value.lastUpdate;
                action.accept(x);
            });
        }

        @Override
        public boolean isChenge() {
            if (value.provider.isChenge())
                value.lastUpdate++;
            return localLastUpdate < value.lastUpdate;
        }

    }

}

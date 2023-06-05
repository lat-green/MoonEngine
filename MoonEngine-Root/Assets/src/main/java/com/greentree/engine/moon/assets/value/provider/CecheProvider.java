package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;

import java.util.function.Consumer;

public class CecheProvider<T> implements ValueProvider<T> {

    public static final int CHARACTERISTICS = CECHED;

    private final ValueProvider<T> provider;

    private transient T cache;

    private CecheProvider(ValueProvider<T> provider) {
        this.provider = provider;
        cache = provider.get();
    }

    public static <T> ValueProvider<T> newProvider(Value<T> value) {
        final var provider = value.openProvider();
        return newProvider(provider);
    }

    public static <T> ValueProvider<T> newProvider(ValueProvider<T> provider) {
        if (provider.hasCharacteristics(CONST) || provider.hasCharacteristics(CECHED))
            return provider;
        return new CecheProvider<>(provider);
    }

    @Override
    public int characteristics() {
        return provider.characteristics() | CHARACTERISTICS;
    }

    @Override
    public T get() {
        provider.tryGet(c -> {
            cache = c;
        });
        return cache;
    }

    @Override
    public ValueProvider<T> copy() {
        return newProvider(provider.copy());
    }

    @Override
    public boolean tryGet(Consumer<? super T> action) {
        return provider.tryGet(c -> {
            cache = c;
            action.accept(cache);
        });
    }

    @Override
    public boolean isChenge() {
        return provider.isChenge();
    }

    @Override
    public T getNotChenge() {
        return cache;
    }

    @Override
    public String toString() {
        return provider.toString();
    }

}

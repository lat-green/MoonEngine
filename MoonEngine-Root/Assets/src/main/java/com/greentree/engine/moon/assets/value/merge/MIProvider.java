package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

import java.util.ArrayList;

public final class MIProvider<T> implements ValueProvider<Iterable<T>> {

    public static final int CHARACTERISTICS = NOT_NULL | CACHED;
    private final Iterable<? extends ValueProvider<? extends T>> providers;

    private MIProvider(Iterable<? extends ValueProvider<? extends T>> providers) {
        this.providers = providers;
    }

    public static <T> ValueProvider<Iterable<T>> newProvider(Iterable<? extends Value<? extends T>> values) {
        final var providers = new ArrayList<ValueProvider<? extends T>>();
        for (var v : values)
            providers.add(v.openProvider());
        return newProviderFromProviders(providers);
    }

    public static <T> ValueProvider<Iterable<T>> newProviderFromProviders(
            Iterable<? extends ValueProvider<? extends T>> providers) {
        return new MIProvider<>(providers);
    }

    @Override
    public int characteristics() {
        return CHARACTERISTICS;
    }

    @Override
    public Iterable<T> get() {
        final var result = new ArrayList<T>();
        for (var p : providers)
            result.add(p.get());
        return result;
    }

    @Override
    public ValueProvider<Iterable<T>> copy() {
        final var copies = new ArrayList<ValueProvider<? extends T>>();
        for (var p : providers)
            copies.add(p.copy());
        return new MIProvider<>(copies);
    }

    @Override
    public boolean isChenge() {
        for (var p : providers)
            if (p.isChenge())
                return p.isChenge();
        return false;
    }

    @Override
    public String toString() {
        return providers.toString();
    }

}

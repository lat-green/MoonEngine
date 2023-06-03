package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;

import java.util.function.Consumer;

public abstract class ProxyProvider<T> implements ValueProvider<T> {

    protected final ValueProvider<T> provider;

    public ProxyProvider(Value<T> Value) {
        this(Value.openProvider());
    }

    public ProxyProvider(ValueProvider<T> provider) {
        this.provider = provider;
    }

    @Override
    public int characteristics() {
        return provider.characteristics();
    }

    @Override
    public T get() {
        return provider.get();
    }

    @Override
    public boolean tryGet(Consumer<? super T> action) {
        return provider.tryGet(action);
    }

    @Override
    public boolean isChenge() {
        return provider.isChenge();
    }

}

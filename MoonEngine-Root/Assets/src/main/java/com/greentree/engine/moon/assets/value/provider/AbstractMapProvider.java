package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;

import java.util.function.Consumer;

public abstract class AbstractMapProvider<IN, OUT> implements ValueProvider<OUT> {

    public static final int CHARACTERISTICS = 0;
    protected final ValueProvider<IN> input;

    public AbstractMapProvider(Value<IN> input) {
        this(input.openProvider());
    }

    public AbstractMapProvider(ValueProvider<IN> input) {
        this.input = input;
    }

    @Override
    public final int characteristics() {
        return input.characteristics() & ~CACHED;
    }

    @Override
    public String toString() {
        return "MapProvider [" + input + "]";
    }

    @Override
    public OUT get() {
        final var value = input.get();
        return map(value);
    }

    @Override
    public boolean tryGet(Consumer<? super OUT> action) {
        return input.tryGet(v -> {
            action.accept(map(v));
        });
    }

    @Override
    public boolean isChenge() {
        return input.isChenge();
    }

    protected abstract OUT map(IN in);

}

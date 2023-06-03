package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;

import java.util.function.Consumer;

public class CecheProvider<T> implements ValueProvider<T> {

    public static final int CHARACTERISTICS = CECHED;

    private final ValueProvider<T> provider;

    private transient T ceche;

    private CecheProvider(ValueProvider<T> provider) {
        this.provider = provider;
        ceche = provider.get();
    }

    public static <T> ValueProvider<T> ceche(Value<T> Value) {
        final var provider = Value.openProvider();
        return ceche(provider);
    }

    @Override
    public int characteristics() {
        return provider.characteristics() | CHARACTERISTICS;
    }

    @Override
    public T get() {
        provider.tryGet(c -> {
            ceche = c;
        });
        return ceche;
    }

    @Override
    public boolean isChenge() {
        return provider.isChenge();
    }

    @Override
    public boolean tryGet(Consumer<? super T> action) {
        return provider.tryGet(c -> {
            ceche = c;
            action.accept(ceche);
        });
    }

    @Override
    public ValueProvider<T> copy() {
        return ceche(provider.copy());
    }

    public static <T> ValueProvider<T> ceche(ValueProvider<T> provider) {
        if (provider.hasCharacteristics(CONST) || provider.hasCharacteristics(CECHED))
            return provider;
        return new CecheProvider<>(provider);
    }

    @Override
    public T getNotChenge() {
        return ceche;
    }

    @Override
    public String toString() {
        return "Ceche [" + provider + "]";
    }

}

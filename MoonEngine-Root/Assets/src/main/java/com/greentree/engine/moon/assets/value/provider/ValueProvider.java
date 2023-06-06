package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.getter.ValueGetter;

import java.util.function.Consumer;

public interface ValueProvider<T> extends ValueGetter<T> {

    default boolean isNull() {
        return !hasCharacteristics(NOT_NULL) && get() == null;
    }

    T get();

    default ValueProvider<T> copy() {
        throw new UnsupportedOperationException();
    }

    default boolean tryGet(Consumer<? super T> action) {
        if (!isChenge())
            return false;
        action.accept(get());
        return true;
    }

    boolean isChenge();

}

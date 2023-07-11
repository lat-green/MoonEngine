package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.ValueCharacteristics;

import java.util.function.Consumer;

public interface ValueProvider<T> extends ValueCharacteristics<T> {

    default boolean isNull() {
        return !hasCharacteristics(NOT_NULL) && get() == null;
    }

    T get();

    default T getNotChenge() {
        return get();
    }

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

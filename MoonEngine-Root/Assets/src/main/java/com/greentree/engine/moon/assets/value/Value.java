package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.provider.ValueProvider;

import java.io.Serializable;

public interface Value<T> extends ValueCharacteristics<T>, Serializable {

    default boolean isNull() {
        return !hasCharacteristics(NOT_NULL) && get() == null;
    }

    default T get() {
        return openProvider().get();
    }

    ValueProvider<T> openProvider();

}

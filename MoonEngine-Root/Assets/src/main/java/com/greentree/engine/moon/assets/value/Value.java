package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.getter.ValueGetter;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

import java.io.Serializable;

public interface Value<T> extends ValueCharacteristics<T>, Serializable {

    default boolean isNull() {
        return !hasCharacteristics(NOT_NULL) && get() == null;
    }

    default T get() {
        return openGetter().get();
    }

    default ValueGetter<T> openGetter() {
        return openProvider();
    }

    ValueProvider<T> openProvider();

}

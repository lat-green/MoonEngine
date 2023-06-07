package com.greentree.engine.moon.assets.value.getter;

import com.greentree.engine.moon.assets.value.ValueCharacteristics;

import java.io.Serializable;

public interface ValueGetter<T> extends ValueCharacteristics<T>, Serializable {

    default T getNotChenge() {
        return get();
    }

    T get();

    ValueGetter<T> copy();

}

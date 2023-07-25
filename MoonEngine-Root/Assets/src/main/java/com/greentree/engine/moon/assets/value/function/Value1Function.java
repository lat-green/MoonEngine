package com.greentree.engine.moon.assets.value.function;

import java.io.Serializable;
import java.util.function.Function;

public interface Value1Function<T, R> extends Function<T, R>, Serializable {

    default R applyWithDest(T value, R dest) {
        return apply(value);
    }

    @Override
    R apply(T value);

}

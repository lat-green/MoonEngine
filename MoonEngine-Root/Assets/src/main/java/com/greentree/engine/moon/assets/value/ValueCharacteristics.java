package com.greentree.engine.moon.assets.value;

import java.util.ArrayList;

public interface ValueCharacteristics<T> {

    int NOT_NULL = 0x00000001;
    int DISTINCT_CHANGE = 0x00000004;
    int CACHED = 0x00000008;
    int CONST = 0x00000010 | CACHED;
    int ONE_PROVIDER = 0x00000020;

    default boolean hasCharacteristics(int characteristics) {
        return (characteristics() & characteristics) == characteristics;
    }

    int characteristics();

    static String toString(int characteristics) {
        final var result = new ArrayList<>(5);
        if ((characteristics & CONST) != 0)
            result.add("CONST");
        if ((characteristics & CACHED) != 0)
            result.add("CECHED");
        if ((characteristics & DISTINCT_CHANGE) != 0)
            result.add("DISTINCT_CHANGE");
        if ((characteristics & NOT_NULL) != 0)
            result.add("NOT_NULL");
        return result.toString();
    }

}

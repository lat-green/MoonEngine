package com.greentree.engine.moon.assets.value;

import java.util.StringJoiner;

public interface ValueCharacteristics<T> {

    int NOT_NULL = 0x00000001;
    int DISTINCT_CHANGE = 0x00000004;
    int CACHED = 0x00000008;
    int CONST = 0x00000010 | CACHED;
    int ONE_PROVIDER = 0x00000020;

    default boolean hasCharacteristics(int characteristics) {
        return hasCharacteristics(characteristics(), characteristics);
    }

    static boolean hasCharacteristics(int characteristicsMask, int characteristics) {
        return (characteristicsMask & characteristics) == characteristics;

    }

    int characteristics();

    static String toString(int characteristics) {
        final var result = new StringJoiner(", ", "[", "]");
        if (hasCharacteristics(characteristics, CONST))
            result.add("CONST");
        if (hasCharacteristics(characteristics, CACHED))
            result.add("CACHED");
        if (hasCharacteristics(characteristics, DISTINCT_CHANGE))
            result.add("DISTINCT_CHANGE");
        if (hasCharacteristics(characteristics, NOT_NULL))
            result.add("NOT_NULL");
        if (hasCharacteristics(characteristics, ONE_PROVIDER))
            result.add("ONE_PROVIDER");
        return result.toString();
    }

}

package com.greentree.common.assets.value;

import com.greentree.engine.moon.assets.value.MutableValue;
import com.greentree.engine.moon.assets.value.Values;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergeTest {

    private static final String TEXT1 = "A";
    private static final String TEXT2 = "B";

    @Test
    void test_NEW() {
        final var v1 = new MutableValue<>(TEXT1);
        final var v2 = new MutableValue<>(TEXT2);
        final var m = Values.merge(v1, v2);
        final var p = m.openProvider();
        assertFalse(p.isChenge());
        v1.set(TEXT1);
        assertTrue(p.isChenge());
        p.get();
        v1.set(null);
        assertTrue(p.isChenge());
        v1.set(TEXT1);
        p.get();
    }

}

package com.greentree.common.assets.value;

import com.greentree.engine.moon.assets.value.MutableValue;
import com.greentree.engine.moon.assets.value.ReduceValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReduceValueTest {

    @Test
    void test_NEW() {
        final var v1 = new MutableValue<>(1);
        final var v2 = new MutableValue<>(2);
        final var r = new ReduceValue<>(v1);
        final var p = r.openProvider();
        assertEquals(p.get(), 1);
        r.set(v2);
        assertEquals(p.get(), 2);
    }

}

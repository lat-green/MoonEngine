package com.greentree.common.assets.value;

import com.greentree.engine.moon.assets.value.NullValue;
import com.greentree.engine.moon.assets.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NullValueTest {

    @Test
    void test_NEW() {
        final var v1 = NullValue.instance();
        assertNull(v1.get());
        assertTrue(v1.hasCharacteristics(Value.CONST));
        assertTrue(v1.isNull());
    }

}

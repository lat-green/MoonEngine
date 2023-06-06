package test.com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultValueTest {

    private static final String TEXT1 = "A";
    private static final String TEXT2 = "B";

    @Test
    void test_NEW() {
        final var v1 = new MutableValue<>(TEXT1);
        final var v2 = new MutableValue<>(TEXT2);
        final var m = DefaultValue.newValue(v1, v2);
        assertFalse(m.hasCharacteristics(Value.CONST));
        assertFalse(m.isNull());
        final var p = m.openProvider();
        assertEquals(p.get(), TEXT1);
        v1.set(null);
        assertEquals(p.get(), TEXT2);
        v1.set(TEXT1);
        assertEquals(p.get(), TEXT1);
    }

    @Test
    void test_NEW_of_CONST_and_MUTABLE() {
        final var v1 = ConstValue.newValue(TEXT1);
        final var v2 = new MutableValue<>(TEXT1);
        final var m = DefaultValue.newValue(v1, v2);
        assertSame(m, v1);
    }

    @Test
    void test_NEW_of_ONE() {
        final var v1 = new MutableValue<>(TEXT1);
        final var m = DefaultValue.newValue(v1);
        assertSame(m, v1);
    }

    @Test
    void test_NEW_of_ONE_and_NULL() {
        final var v1 = new MutableValue<>(TEXT1);
        final var v2 = NullValue.<String>instance();
        final var m = DefaultValue.newValue(v1, v2);
        assertEquals(m.get(), TEXT1);
    }

}

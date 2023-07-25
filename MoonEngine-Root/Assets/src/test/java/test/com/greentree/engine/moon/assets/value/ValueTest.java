package test.com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.MutableValue;
import com.greentree.engine.moon.assets.value.merge.M2Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueTest {

    private static final String TEXT_1 = "A";
    private static final String TEXT_2 = "B";
    private static final String TEXT_3 = "C";

    @Test
    void test1() {
        final var v1 = new MutableValue<>(TEXT_1);
        final var v2 = new MutableValue<>(TEXT_2);
        final var m = new M2Value<>(v1, v2).openProvider();
        assertEquals(m.get().t1, TEXT_1);
        v1.set(TEXT_3);
        assertEquals(m.get().t1, TEXT_3);
    }

}

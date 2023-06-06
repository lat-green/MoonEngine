package test.com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.MutableValue;
import com.greentree.engine.moon.assets.value.ValueFunctionMapValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapValueTest {

    @Test
    void test1() {
        var input = new MutableValue<>("A");
        var map = ValueFunctionMapValue.newValue(input, x -> "(" + x + ")");
        var p = map.openProvider();
        assertEquals(p.get(), "(A)");
        input.set("B");
        assertTrue(p.isChenge());
        assertEquals(p.get(), "(B)");
    }

}

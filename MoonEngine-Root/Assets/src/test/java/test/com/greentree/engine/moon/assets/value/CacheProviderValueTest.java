package test.com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.CacheProviderValue;
import com.greentree.engine.moon.assets.value.MutableValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CacheProviderValueTest {

    @Test
    void test1() {
        var input = new MutableValue<>("A");
        var value = CacheProviderValue.newValue(input);
        var p1 = value.openProvider();
        var p2 = value.openProvider();
        input.set("B");
        assertTrue(p1.isChenge());
        assertTrue(p2.isChenge());
        p1.get();
        assertFalse(p1.isChenge());
        assertTrue(p2.isChenge());
        assertFalse(p1.isChenge());
        assertTrue(p2.isChenge());
    }

}

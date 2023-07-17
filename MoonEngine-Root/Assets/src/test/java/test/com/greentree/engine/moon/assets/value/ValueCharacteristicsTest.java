package test.com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.ValueCharacteristics;
import org.junit.jupiter.api.Test;

import static com.greentree.engine.moon.assets.value.ValueCharacteristics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueCharacteristicsTest {

    @Test
    void testToString() {
        var characteristics = NOT_NULL | ONE_PROVIDER;
        var test = ValueCharacteristics.toString(characteristics);
        assertEquals(test, "[NOT_NULL, ONE_PROVIDER]");
    }

    @Test
    void testToString2() {
        var characteristics = CONST | ONE_PROVIDER;
        var test = ValueCharacteristics.toString(characteristics);
        assertEquals(test, "[CONST, CACHED, ONE_PROVIDER]");
    }

    @Test
    void testToString3() {
        var characteristics = CACHED | ONE_PROVIDER;
        var test = ValueCharacteristics.toString(characteristics);
        assertEquals(test, "[CACHED, ONE_PROVIDER]");
    }

}

package test.com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.MutableValue;
import com.greentree.engine.moon.assets.value.NullValue;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ValueSerializableTest {

    @Test
    void MutableValue_after_Serialization() throws ClassNotFoundException, IOException {
        final var str = "Hello World";
        final var strv = new MutableValue<>(str);
        @SuppressWarnings("unchecked") final var c = (MutableValue<String>) deser(ser(strv));
        assertEquals(strv.get(), c.get());
    }

    @SuppressWarnings("unchecked")
    private <T> T deser(byte[] ser) throws IOException, ClassNotFoundException {
        try (final var bout = new ByteArrayInputStream(ser)) {
            try (final var oout = new ObjectInputStream(bout)) {
                return (T) oout.readObject();
            }
        }
    }

    private byte[] ser(Object obj) throws IOException {
        try (final var bout = new ByteArrayOutputStream()) {
            try (final var oout = new ObjectOutputStream(bout)) {
                oout.writeObject(obj);
            }
            return bout.toByteArray();
        }
    }

    @Test
    void NullValue_Serialization() throws ClassNotFoundException, IOException {
        final var v1 = NullValue.instance();
        final var v2 = deser(ser(v1));
        assertEquals(v1, v2);
        assertSame(v1, v2);
    }

}

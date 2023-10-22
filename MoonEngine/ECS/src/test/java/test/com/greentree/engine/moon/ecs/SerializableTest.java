package test.com.greentree.engine.moon.ecs;

import com.greentree.engine.moon.ecs.ClassSetEntity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static test.com.greentree.engine.moon.ecs.TestUtil.*;

public class SerializableTest {

    static Stream<Integer> ints() {
        return Stream.of(0, 9, 8, -13, 42);
    }

    @MethodSource("ints")
    @ParameterizedTest
    void ACompnent_ser_deser(int value) throws IOException, ClassNotFoundException {
        final var a = new AComponent();
        a.value = value;
        final var ser = ser(a);
        final var c = deser(ser);
        assertNotSame(a, c);
        assertEquals(a, c);
    }

    @MethodSource("ints")
    @ParameterizedTest
    void Entity_ser_deser(int value) throws IOException, ClassNotFoundException {
        final var a = new AComponent();
        a.value = value;
        final var entity = new ClassSetEntity();
        entity.add(a);
        final var ser = ser(entity);
        final var c = ClassSetEntity.load(ser);
        assertComponentEquals(entity, c);
    }

}

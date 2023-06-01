package test.com.greentree.engine.moon.ecs;

import com.greentree.engine.moon.ecs.CollectionWorld;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CopyTest {

    static Stream<Integer> ints() {
        return Stream.of(0, 9, 8, -13, 42);
    }

    @MethodSource("ints")
    @ParameterizedTest
    void ACompnent(int value) throws IOException, ClassNotFoundException {
        final var a = new ACompnent();
        a.value = value;
        final var c = a.copy();
		assertNotSame(a, c);
        assertEquals(a, c);
    }

    @MethodSource("ints")
    @ParameterizedTest
    void Entity(int value) throws IOException, ClassNotFoundException {
        final var world = new CollectionWorld();
        final var entity = world.newEntity();
        final var a = new ACompnent();
        entity.add(a);
        a.value = value;
        final var c = entity.copy();
        TestUtil.assertComponentEquals(entity, c);
    }

    @MethodSource("ints")
    @ParameterizedTest
    void PrototypeEntity(int value) throws IOException, ClassNotFoundException {
        final var world = new CollectionWorld();
        final var entity = world.newEntity();
        {
            final var a = new ACompnent();
            entity.add(a);
            a.value = value;
        }
        final var prototype = entity.copy();
        TestUtil.assertComponentEquals(entity, prototype);
        assertTrue(world.isActive(entity));
        assertFalse(world.isActive(prototype));
        final var ec = entity.copy(world);
        TestUtil.assertComponentEquals(entity, ec);
        assertTrue(world.isActive(ec));
        final var pc = prototype.copy(world);
        TestUtil.assertComponentEquals(prototype, pc);
        assertTrue(world.isActive(pc));
    }

}

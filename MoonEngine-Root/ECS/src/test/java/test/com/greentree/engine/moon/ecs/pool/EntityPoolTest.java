package test.com.greentree.engine.moon.ecs.pool;

import com.greentree.engine.moon.ecs.ClassSetEntity;
import com.greentree.engine.moon.ecs.CollectionWorld;
import com.greentree.engine.moon.ecs.pool.EmptyEntityStrategy;
import com.greentree.engine.moon.ecs.pool.EntityPoolStrategy;
import com.greentree.engine.moon.ecs.pool.PrototypeEntityStrategy;
import com.greentree.engine.moon.ecs.pool.StackEntityPool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.com.greentree.engine.moon.ecs.ACompnent;
import test.com.greentree.engine.moon.ecs.TestUtil;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityPoolTest {

    static Stream<EntityPoolStrategy> strategies() {
        final var p = new ClassSetEntity();
        p.add(new ACompnent());
        return Stream.of(new PrototypeEntityStrategy(p), new EmptyEntityStrategy());
    }

    @MethodSource("strategies")
    @DisplayName("strategies")
    @ParameterizedTest
    void testPool(EntityPoolStrategy str) {
        final var world = new CollectionWorld();
        try (final var pool = new StackEntityPool(world, str)) {
            final var e = pool.get();
            pool.free(e);
            final var g = pool.get();
            assertEquals(g, e);
        }
    }

    @MethodSource("strategies")
    @DisplayName("strategies After Serialization")
    @ParameterizedTest
    void testPool_after_deser(EntityPoolStrategy str)
            throws ClassNotFoundException, IOException {
        final var world = CollectionWorld.load(TestUtil.ser(new CollectionWorld()));
        try (final var pool = new StackEntityPool(world, str)) {
            final var e = pool.get();
            pool.free(e);
            final var g = pool.get();
            assertEquals(g, e);
        }
    }

}

package test.com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.World;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public interface FilterTests {

    @Test
    default void test1() {
        runWorld(world -> {
            var filter = world.newFilter();
            assertNotNull(filter);
        });

    }

    void runWorld(Consumer<? super World> worldConsumer);

}

package test.com.greentree.engine.moon.ecs;

import com.greentree.commons.tests.DisabledIfRunInIDE;
import com.greentree.engine.moon.ecs.ClassSetEntity;
import com.greentree.engine.moon.ecs.CollectionWorld;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class WorldTest {

    @Nested
    class Actives {

        @Test
        void isActive_isDeactive_deleteEntity() {
            final var world = new CollectionWorld();
            final var e = world.newEntity();
            world.deleteEntity(e);
            assertFalse(world.isDeactivate(e));
            assertFalse(world.isActive(e));
        }

        @Test
        void isActive_isDeactive_new_Entity() {
            final var world = new CollectionWorld();
            final var e = new ClassSetEntity();
            assertFalse(world.isDeactivate(e));
            assertFalse(world.isActive(e));
        }

        @Test
        void isActive_isDeactive_newDeactiveEntity() {
            final var world = new CollectionWorld();
            final var e = world.newDeactivateEntity();
            assertTrue(world.isDeactivate(e));
            assertFalse(world.isActive(e));
        }

        @Test
        void isActive_isDeactive_newEntity() {
            final var world = new CollectionWorld();
            final var e = world.newEntity();
            assertFalse(world.isDeactivate(e));
            assertTrue(world.isActive(e));
        }

        @Test
        void isActive_isDeactive_newEntity_da() {
            final var world = new CollectionWorld();
            final var e = world.newEntity();
            world.deactivate(e);
            world.active(e);
            assertFalse(world.isDeactivate(e));
            assertTrue(world.isActive(e));
        }

    }

    @DisabledIfRunInIDE
    @Nested
    class Timeouts {

        @Test
        void timeout_isActive() {
            final var world = new CollectionWorld();
            final var e = world.newEntity();
            assertTimeout(Duration.ofSeconds(2), () -> {
                var t = 1_000_000;
                while (t-- > 0)
                    world.isActive(e);
                world.clear();
            });
        }

        @Test
        void timeout_newDeactiveEntity() {
            final var world = new CollectionWorld(1_000_000);
            assertTimeout(Duration.ofSeconds(2), () -> {
                var t = 1_000_000;
                while (t-- > 0)
                    world.newDeactivateEntity();
            });
            world.clear();
        }

        @Test
        void timeout_newDeactiveEntity_deleteEntity() {
            final var world = new CollectionWorld();
            assertTimeout(Duration.ofSeconds(2), () -> {
                var t = 1_000_000;
                while (t-- > 0) {
                    final var e = world.newDeactivateEntity();
                    world.deleteEntity(e);
                }
            });
            world.clear();
        }

        @Test
        void timeout_newEntity() {
            final var world = new CollectionWorld(1_000_000);
            assertTimeout(Duration.ofSeconds(4), () -> {
                var t = 1_000_000;
                while (t-- > 0)
                    world.newEntity();
            });
            world.clear();
        }

        @Test
        void timeout_newEntity_chunck() {
            final var world = new CollectionWorld();
            assertTimeout(Duration.ofSeconds(2), () -> {
                var t = 1_000;
                while (t-- > 0) {
                    var t0 = 1_000;
                    while (t0-- > 0)
                        world.newEntity();
                    world.clear();
                }
            });
        }

        @Test
        void timeout_newEntity_deleteEntity() {
            final var world = new CollectionWorld();
            assertTimeout(Duration.ofSeconds(2), () -> {
                var t = 1_000_000;
                while (t-- > 0) {
                    final var e = world.newEntity();
                    world.deleteEntity(e);
                }
            });
            world.clear();
        }

    }

    @Test
    void newDeactiveEntity_deleteEntity() {
        final var world = new CollectionWorld();
        final var e = world.newDeactivateEntity();
        world.deleteEntity(e);
    }

    @Test
    void newEntity_deactive_deleteEntity() {
        final var world = new CollectionWorld();
        final var e = world.newEntity();
        world.deactivate(e);
        world.deleteEntity(e);
    }

    @Test
    void test_delete_not_my_Entity() {
        final var world = new CollectionWorld();
        final var e = new ClassSetEntity();
        assertThrows(UnsupportedOperationException.class, () -> {
            world.deleteEntity(e);
        });
    }

}

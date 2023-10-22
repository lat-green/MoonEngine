package test.com.greentree.engine.moon.ecs;

import com.greentree.engine.moon.ecs.ArchetypeWorld;
import com.greentree.engine.moon.ecs.World;

import java.util.function.Consumer;

public class ArchetypeWorldTests extends WorldTest {

    @Override
    public void runWorld(Consumer<? super World> worldConsumer) {
        var world = new ArchetypeWorld();
        worldConsumer.accept(world);
    }

}

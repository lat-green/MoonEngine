package test.com.greentree.engine.moon.ecs;

import com.greentree.engine.moon.ecs.CollectionWorld;
import com.greentree.engine.moon.ecs.World;

import java.util.function.Consumer;

public class CollectionWorldTests extends WorldTest {

    @Override
    public void runWorld(Consumer<? super World> worldConsumer) {
        var world = new CollectionWorld();
        worldConsumer.accept(world);
    }

}

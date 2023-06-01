package test.com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.CollectionWorld;
import com.greentree.engine.moon.ecs.World;

import java.util.function.Consumer;

public class CollectionWorldFilterTests implements FilterTests {

    @Override
    public void runWorld(Consumer<? super World> worldConsumer) {
        var world = new CollectionWorld();
        worldConsumer.accept(world);
    }

}

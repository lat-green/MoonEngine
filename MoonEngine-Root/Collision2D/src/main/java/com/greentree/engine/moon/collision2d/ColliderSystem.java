package com.greentree.engine.moon.collision2d;

import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.geometry.geom2d.collision.strategy.CollisionStrategy;
import com.greentree.commons.geometry.geom2d.collision.strategy.SortCollisionStrategy;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.ClassSetEntity;
import com.greentree.engine.moon.ecs.PrototypeEntity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.pool.EntityPool;
import com.greentree.engine.moon.ecs.pool.PrototypeEntityStrategy;
import com.greentree.engine.moon.ecs.pool.StackEntityPool;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

import java.util.stream.StreamSupport;

public class ColliderSystem implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder().require(ColliderComponent.class);
    private static final FilterBuilder BUILDER_EVENT = new FilterBuilder().require(ColliderEventComponent.class);
    private static final PrototypeEntity PROTOTYPE_EVENT = new ClassSetEntity();

    static {
        PROTOTYPE_EVENT.add(new ColliderEventComponent());
    }

    private final CollisionStrategy strategy = new SortCollisionStrategy();
    private Filter<?> filter_event;
    private Filter<?> filter;
    private EntityPool pool_event;

    @Override
    public void update() {
        for (var e : filter) {
            var t = e.get(Transform.class);
            var c = e.get(ColliderComponent.class).shape();
            c.moveTo(t.position.x(), t.position.y());
        }
        var a = StreamSupport.stream(filter.spliterator(), false).map(e -> new Collidable2DEntity(e)).toList();
        var c = new Collidable2D[a.size()];
        a.toArray(c);
        var cEvent = strategy.getCollisions(c);
        for (var e : filter_event)
            e.delete();
        for (var event : cEvent) {
            var e = pool_event.get();
            e.get(ColliderEventComponent.class).set(event);
        }
    }

    @Override
    public void init(World world, SceneProperties sceneProperties) {
        filter = BUILDER.build(world);
        filter_event = BUILDER_EVENT.build(world);
        pool_event = new StackEntityPool(world, new PrototypeEntityStrategy(PROTOTYPE_EVENT));
    }

}

package com.greentree.engine.moon.collision2d;

import com.greentree.commons.geometry.geom2d.IMovableShape2D;
import com.greentree.commons.geometry.geom2d.IShape2D;
import com.greentree.commons.geometry.geom2d.collision.strategy.CollisionStrategy;
import com.greentree.commons.geometry.geom2d.collision.strategy.SortCollisionStrategy;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.ClassSetEntity;
import com.greentree.engine.moon.ecs.PrototypeEntity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.pool.EntityPool;
import com.greentree.engine.moon.ecs.pool.PrototypeEntityStrategy;
import com.greentree.engine.moon.ecs.pool.StackEntityPool;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

import java.util.ArrayList;

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
        var world = new ArrayList<Collidable2DEntity>();
        for (var e : filter) {
            var t = e.get(Transform.class);
            var c = e.get(ColliderComponent.class).shape();
            var shape = clone(c);
            shape.moveTo(t.position.x(), t.position.y());
            world.add(new Collidable2DEntity(e, shape));
        }
        var c = new Collidable2DEntity[world.size()];
        world.toArray(c);
        var cEvent = strategy.getCollisions(c);
        var copy_filter_event = new ArrayList<WorldEntity>();
        for (var e : filter_event)
            copy_filter_event.add(e);
        for (var e : copy_filter_event)
            e.delete();
        for (var event : cEvent) {
            var e = pool_event.get();
            e.get(ColliderEventComponent.class).set(event);
        }
    }

    private static IMovableShape2D clone(IShape2D shape) {
        if (shape instanceof Circle2D c) {
            var center = c.getCenter();
            return new Circle2D(center.x(), center.y(), c.getRadius());
        }
        throw new UnsupportedOperationException("shape:" + shape);
    }

    @Override
    public void init(World world, SceneProperties sceneProperties) {
        filter = BUILDER.build(world);
        filter_event = BUILDER_EVENT.build(world);
        pool_event = new StackEntityPool(world, new PrototypeEntityStrategy(PROTOTYPE_EVENT));
    }

}

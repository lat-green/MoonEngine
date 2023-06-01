package com.greentree.engine.moon.ecs.pool;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;

import java.util.ArrayList;
import java.util.List;

public final class StackEntityPool implements EntityPool {

    private final List<WorldEntity> pool = new ArrayList<>();
    private final World world;
    private final EntityPoolStrategy strategy;

    public StackEntityPool(World world, EntityPoolStrategy strategy) {
        this.world = world;
        this.strategy = strategy;
    }

    public WorldEntity get() {
        final WorldEntity e;
        if (pool.isEmpty()) {
            e = world.newEntity();
        } else {
            e = IteratorUtil.min(pool, strategy.comporator());
            pool.remove(e);
            e.activate();
        }
        strategy.toInstance(e);
        return e;
    }

    @Override
    public void free(WorldEntity entity) {
        if (entity.isActive())
            entity.deactivate();
        pool.add(entity);
    }

}

package com.greentree.engine.moon.ecs.pool;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;

import java.io.Serializable;

public final class ArrayLimitEntityPool implements EntityPool {

    private final EntityInfo[] pool;

    private final World world;
    private final EntityPoolStrategy strategy;

    public ArrayLimitEntityPool(World world, int limit,
                                EntityPoolStrategy strategy) {
        this.world = world;
        this.strategy = strategy;
        pool = new EntityInfo[limit];
        for (var i = 0; i < limit; i++)
            pool[i] = new EntityInfo();
    }

    @Override
    public void close() {
        for (var i = 0; i < pool.length; i++) {
            final var e = pool[i].entity;
            e.delete();
            pool[i] = null;
        }
    }

    @Override
    public void free(WorldEntity entity) {
        for (var e : pool)
            if (entity == e.entity) {
                if (!e.entity.isActive())
                    e.entity = world.newDeactivateEntity();
                entity.deactivate();
                e.use = false;
                return;
            }
        throw new IllegalArgumentException("not my entity");
    }

    @Override
    public WorldEntity get() {
        for (var e : pool)
            if (!e.use) {
                if (!e.entity.isDeactivate())
                    e.entity = world.newDeactivateEntity();
                e.use = true;
                strategy.toInstance(e.entity);
                e.entity.activate();
                return e.entity;
            }
        return null;
    }

    public int limit() {
        return pool.length;
    }

    private final class EntityInfo implements Serializable {

        private static final long serialVersionUID = 1L;
        private WorldEntity entity;
        private boolean use;

        public EntityInfo() {
            this(world.newDeactivateEntity());
        }

        public EntityInfo(WorldEntity entity) {
            this(entity, false);
        }

        public EntityInfo(WorldEntity entity, boolean use) {
            this.entity = entity;
            this.use = use;
        }

        @Override
        public String toString() {
            return "EntityInfo [" + entity + ", " + use + "]";
        }

    }

}

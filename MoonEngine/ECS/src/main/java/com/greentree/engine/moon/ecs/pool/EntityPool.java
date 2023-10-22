package com.greentree.engine.moon.ecs.pool;

import com.greentree.engine.moon.ecs.WorldEntity;

public interface EntityPool extends AutoCloseable {

    WorldEntity get();

    @Override
    default void close() {
    }

}

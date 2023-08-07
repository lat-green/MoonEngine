package com.greentree.engine.moon.base.assets.scene;

public interface Constructor<T> extends AutoCloseable {

    @Override
    default void close() {
    }

    T value();

}

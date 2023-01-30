package com.greentree.engine.moon.ecs.component;

import java.io.Serializable;

public interface Component extends Serializable, AutoCloseable {
	
	@Override
	default void close() {
	}
	
	default boolean copyTo(Component other) {
		return false;
	}
	
	Component copy();
	
}

package com.greentree.engine.moon.ecs.pool;

import com.greentree.engine.moon.ecs.Entity;

public interface EntityPool extends AutoCloseable {
	
	void free(Entity entity);
	Entity get();
	@Override
	default void close() {
		
	}
}

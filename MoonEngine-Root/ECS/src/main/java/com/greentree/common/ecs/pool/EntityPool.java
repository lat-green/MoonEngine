package com.greentree.common.ecs.pool;

import com.greentree.common.ecs.Entity;

public interface EntityPool extends AutoCloseable {
	
	void free(Entity entity);
	Entity get();
	@Override
	default void close() {
		
	}
}

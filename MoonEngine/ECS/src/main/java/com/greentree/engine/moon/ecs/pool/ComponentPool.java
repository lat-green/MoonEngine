package com.greentree.engine.moon.ecs.pool;

import com.greentree.engine.moon.ecs.component.Component;

public interface ComponentPool<T extends Component> {
	
	Class<T> type();
	T get();
	void free(T component);
	
}

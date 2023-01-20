package com.greentree.common.ecs.pool;

import com.greentree.common.ecs.component.Component;

public interface ComponentPool<T extends Component> {
	
	Class<T> type();
	T get();
	void free(T component);
	
}

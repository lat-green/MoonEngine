package com.greentree.engine.moon.ecs;

import com.greentree.engine.moon.ecs.component.Component;

public interface ComponentLock extends AutoCloseable {
	
	@Override
	void close();
	
	void remove(Class<? extends Component> componentClass);
	
	void add(Component component);
	
}

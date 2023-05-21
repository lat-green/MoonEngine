package com.greentree.engine.moon.ecs;

import com.greentree.engine.moon.ecs.ClassSet.LockClassSet;
import com.greentree.engine.moon.ecs.component.Component;

public record ClassSetComponentLock(LockClassSet<Component> lock) implements ComponentLock {
	
	@Override
	public void close() {
		lock.close();
	}
	
	@Override
	public void remove(Class<? extends Component> componentClass) {
		lock.remove(componentClass);
	}
	
	@Override
	public void add(Component component) {
		lock.add(component);
	}
	
	@Override
	public <C extends Component> C get(Class<C> componentClass) {
		return lock.get(componentClass);
	}
	
	
}

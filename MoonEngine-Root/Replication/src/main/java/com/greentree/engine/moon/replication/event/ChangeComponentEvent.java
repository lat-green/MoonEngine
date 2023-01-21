package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.ClassSet.LockClassSet;
import com.greentree.engine.moon.ecs.component.Component;

public record ChangeComponentEvent(Component component) implements ComponentEvent {

	@Override
	public void accept(LockClassSet<Component> lock) {
		final var cls = component.getClass();
		if(!component.copyTo(lock.get(cls))) {
			lock.remove(cls);
			lock.add(component);
		}
	}

}

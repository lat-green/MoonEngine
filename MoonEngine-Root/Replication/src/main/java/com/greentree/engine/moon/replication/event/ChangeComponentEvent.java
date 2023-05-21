package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.ComponentLock;
import com.greentree.engine.moon.ecs.component.Component;

public record ChangeComponentEvent(Component component) implements ComponentEvent {

	@Override
	public void accept(ComponentLock lock) {
		final var cls = component.getClass();
		if(!component.copyTo(lock.get(cls))) {
			lock.remove(cls);
			lock.add(component);
		}
	}

}

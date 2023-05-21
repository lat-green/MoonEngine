package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.ComponentLock;
import com.greentree.engine.moon.ecs.component.Component;

public record AddComponentEvent(Component component) implements ComponentEvent {

	@Override
	public void accept(ComponentLock lock) {
		lock.add(component);
	}



}

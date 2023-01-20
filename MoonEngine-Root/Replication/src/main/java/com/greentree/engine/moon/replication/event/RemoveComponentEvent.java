package com.greentree.engine.moon.replication.event;

import com.greentree.common.ecs.ClassSet.LockClassSet;
import com.greentree.common.ecs.component.Component;

public record RemoveComponentEvent(Class<? extends Component> cls) implements ComponentEvent {

	@Override
	public void accept(LockClassSet<Component> lock) {
		lock.remove(cls);
	}



}

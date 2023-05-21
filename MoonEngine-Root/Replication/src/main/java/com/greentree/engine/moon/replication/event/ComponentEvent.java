package com.greentree.engine.moon.replication.event;

import java.io.Serializable;
import java.util.function.Consumer;

import com.greentree.engine.moon.ecs.ComponentLock;

public interface ComponentEvent extends Serializable, Consumer<ComponentLock> {

	@Override
	void accept(ComponentLock lock);
	
}

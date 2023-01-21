package com.greentree.engine.moon.replication.event;

import java.io.Serializable;
import java.util.function.Consumer;

import com.greentree.engine.moon.ecs.ClassSet.LockClassSet;
import com.greentree.engine.moon.ecs.component.Component;

public interface ComponentEvent extends Serializable, Consumer<LockClassSet<Component>> {

	@Override
	void accept(LockClassSet<Component> lock);
	
}

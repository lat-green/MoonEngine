package com.greentree.engine.moon.replication.event;

import java.io.Serializable;
import java.util.function.Consumer;

import com.greentree.engine.moon.ecs.World;

public interface EntityEvent extends Serializable, Consumer<World> {

	@Override
	void accept(World world);
	
}

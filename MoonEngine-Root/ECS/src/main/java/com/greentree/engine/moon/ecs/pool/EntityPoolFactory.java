package com.greentree.engine.moon.ecs.pool;

import java.io.Serializable;

import com.greentree.engine.moon.ecs.World;

public interface EntityPoolFactory extends Serializable {

	EntityPool get(World world);
	
}

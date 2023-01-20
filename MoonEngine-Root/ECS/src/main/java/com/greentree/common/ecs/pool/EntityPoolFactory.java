package com.greentree.common.ecs.pool;

import java.io.Serializable;

import com.greentree.common.ecs.World;

public interface EntityPoolFactory extends Serializable {

	EntityPool get(World world);
	
}

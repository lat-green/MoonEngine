package com.greentree.engine.moon.ecs.pool;

import com.greentree.engine.moon.ecs.World;


public final class StackEntityPoolFactory implements EntityPoolFactory {
	private static final long serialVersionUID = 1L;

	private final EntityPoolStrategy strategy;
	
	public StackEntityPoolFactory(EntityPoolStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public StackEntityPool get(World world) {
		return new StackEntityPool(world, strategy);
	}

}

package com.greentree.common.ecs.pool;

import java.util.ArrayList;
import java.util.List;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;
import com.greentree.commons.util.iterator.IteratorUtil;


public final class StackEntityPool implements EntityPool {
	
	private final List<Entity> pool = new ArrayList<>();
	private final World world;
	private final EntityPoolStrategy strategy;

	public StackEntityPool(World world, EntityPoolStrategy strategy) {
		this.world = world;
		this.strategy = strategy;
	}

	public Entity get() {
		final Entity e;
		if(pool.isEmpty()) {
			e = world.newEntity();
		} else {
			e = IteratorUtil.min(pool, strategy.comporator());
			pool.remove(e);
			world.active(e);
		}
		strategy.toInstance(e);
		return e;
	}

	@Override
	public void free(Entity entity) {
		if(world.isActive(entity))
			world.deactive(entity);
		pool.add(entity);
	}
	
}

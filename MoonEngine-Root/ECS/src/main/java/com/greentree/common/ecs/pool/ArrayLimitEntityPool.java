package com.greentree.common.ecs.pool;

import java.io.Serializable;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;


public final class ArrayLimitEntityPool implements EntityPool {
	
	private final EntityInfo[] pool;
	
	private final World world;
	private final EntityPoolStrategy strategy;
	public ArrayLimitEntityPool(World world, int limit,
			EntityPoolStrategy strategy) {
		this.world = world;
		this.strategy = strategy;
		pool = new EntityInfo[limit];
		for(var i = 0; i < limit; i++)
			pool[i] = new EntityInfo();
	}
	
	@Override
	public void close() {
		for(var i = 0; i < pool.length; i++) {
			final var e = pool[i].entity;
			world.deleteEntity(e);
			pool[i] = null;
		}
	}
	
	@Override
	public void free(Entity entity) {
		for(var e : pool)
			if(entity == e.entity) {
				if(!world.isActive(e.entity))
					e.entity = world.newDeactiveEntity();
				world.deactive(entity);
				e.use = false;
				return;
			}
		throw new IllegalArgumentException("not my entity");
	}
	
	@Override
	public Entity get() {
		for(var e : pool)
			if(!e.use) {
				if(!world.isDeactive(e.entity))
					e.entity = world.newDeactiveEntity();
				e.use = true;
				strategy.toInstance(e.entity);
				world.active(e.entity);
				return e.entity;
			}
		return null;
	}
	
	public int limit() {
		return pool.length;
	}
	
	private final class EntityInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;
		private Entity entity;
		private boolean use;
		
		public EntityInfo() {
			this(world.newDeactiveEntity());
		}
		
		public EntityInfo(Entity entity) {
			this(entity, false);
		}
		
		public EntityInfo(Entity entity, boolean use) {
			this.entity = entity;
			this.use = use;
		}
		
		@Override
		public String toString() {
			return "EntityInfo [" + entity + ", " + use + "]";
		}
		
	}
	
}

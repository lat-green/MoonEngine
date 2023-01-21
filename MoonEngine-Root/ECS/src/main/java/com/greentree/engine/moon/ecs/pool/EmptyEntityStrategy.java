package com.greentree.engine.moon.ecs.pool;

import java.util.Comparator;

import com.greentree.engine.moon.ecs.Entity;

public class EmptyEntityStrategy implements EntityPoolStrategy {
	private static final long serialVersionUID = 1L;
	
	private static final Comparator<Entity> COMPARATOR = Comparator.comparing(e -> e.size());

	@Override
	public Comparator<Entity> comporator() {
		return COMPARATOR;
	}

	@Override
	public void toInstance(Entity entity) {
		entity.clear();
	}

}

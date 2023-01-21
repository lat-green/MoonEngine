package com.greentree.engine.moon.ecs.pool;

import java.io.Serializable;
import java.util.Comparator;

import com.greentree.engine.moon.ecs.Entity;

public interface EntityPoolStrategy extends Serializable {

	Comparator<Entity> comporator();
	void toInstance(Entity entity);

}

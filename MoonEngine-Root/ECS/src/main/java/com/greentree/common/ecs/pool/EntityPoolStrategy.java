package com.greentree.common.ecs.pool;

import java.io.Serializable;
import java.util.Comparator;

import com.greentree.common.ecs.Entity;

public interface EntityPoolStrategy extends Serializable {

	Comparator<Entity> comporator();
	void toInstance(Entity entity);

}

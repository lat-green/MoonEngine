package com.greentree.common.ecs.filter;

import java.util.Comparator;

import com.greentree.common.ecs.Entity;

public interface EntityCollection extends Iterable<Entity> {
	
	boolean add(Entity entity);
	boolean remove(Entity entity);
	
	EntityCollection sort(Comparator<? super Entity> comparator);
	
}

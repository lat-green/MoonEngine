package com.greentree.engine.moon.ecs.filter;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.Entity;

public final class CopyOnWriteArrayListEntityCollection implements EntityCollection {

	private final Iterable<? extends Comparator<? super Entity>> comparators;
	private final List<Entity> entities = new CopyOnWriteArrayList<>();

	public CopyOnWriteArrayListEntityCollection(Iterable<? extends Comparator<? super Entity>> comparators) {
		this.comparators = comparators;
	}
	
	public CopyOnWriteArrayListEntityCollection() {
		this(IteratorUtil.empty());
	}
	
	@Override
	public Iterator<Entity> iterator() {
		return entities.iterator();
	}
	
	@Override
	public boolean add(Entity entity) {
		if(!entities.contains(entity)) {
			final var result = entities.add(entity);
			for(var c : comparators)
				Collections.sort(entities, c);
			return result;
		}
		return false;
	}
	
	@Override
	public boolean remove(Entity entity) {
		return entities.remove(entity);
	}
	
	@Override
	public EntityCollection sort(Comparator<? super Entity> comparator) {
		return new CopyOnWriteArrayListEntityCollection(IteratorUtil.union(IteratorUtil.iterable(comparator), comparators));
	}

}

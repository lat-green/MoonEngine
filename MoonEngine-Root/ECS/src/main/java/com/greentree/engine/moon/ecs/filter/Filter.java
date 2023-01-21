package com.greentree.engine.moon.ecs.filter;

import java.util.Comparator;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;

public interface Filter extends Iterable<Entity>, AutoCloseable {

	World getWorld();
	@Override
	void close();
	
	default Filter sort(Comparator<? super Entity> comparator) {
		return new SortFilter(this, comparator);
	}

	default <T extends Component> Iterable<? extends T> get(Class<T> cls) {
		return IteratorUtil.map(this, e->e.get(cls));
	}
	
}
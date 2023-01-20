package com.greentree.common.ecs.filter;

import java.util.Comparator;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;
import com.greentree.common.ecs.component.Component;
import com.greentree.commons.util.iterator.IteratorUtil;

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
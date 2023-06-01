package com.greentree.engine.moon.ecs.filter;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.component.Component;

import java.util.Comparator;

public interface Filter<E extends Entity> extends Iterable<E>, AutoCloseable {

    @Override
    void close();

    default Filter<E> sort(Comparator<? super E> comparator) {
        return new SortFilter<>(this, comparator);
    }

    default <T extends Component> Iterable<? extends T> get(Class<T> cls) {
        return IteratorUtil.map(this, e -> e.get(cls));
    }

}
package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.Entity;

import java.util.Comparator;

public interface Filter extends FilterBase<Entity> {

    @Override
    void close();

    default Filter sort(Comparator<? super Entity> comparator) {
        return new EntityFilterAdapter(new SortFilter<>(this, comparator));
    }

}
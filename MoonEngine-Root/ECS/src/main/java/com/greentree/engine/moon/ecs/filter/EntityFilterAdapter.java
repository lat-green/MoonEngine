package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.Entity;

import java.util.Iterator;

public record EntityFilterAdapter(FilterBase<Entity> filter) implements Filter {

    @Override
    public void close() {
        filter.close();
    }

    @Override
    public Iterator<Entity> iterator() {
        return filter.iterator();
    }

}

package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public final class SortFilter implements Filter {

    private final Filter filter;
    private final Comparator<? super Entity> comparator;

    public SortFilter(Filter filter, Comparator<? super Entity> comparator) {
        this.filter = filter;
        this.comparator = comparator;
    }

    @Override
    public Iterator<Entity> iterator() {
        final var list = new ArrayList<Entity>();
        for (var e : filter)
            list.add(e);
        Collections.sort(list, comparator);
        return list.iterator();
    }

    @Override
    public void close() {
        filter.close();
    }

}

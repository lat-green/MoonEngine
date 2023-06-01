package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public final class SortFilter<E extends Entity> implements Filter<E> {

    private final Filter<E> filter;
    private final Comparator<? super E> comparator;

    public SortFilter(Filter<E> filter, Comparator<? super E> comparator) {
        this.filter = filter;
        this.comparator = comparator;
    }

    @Override
    public Iterator<E> iterator() {
        final var list = new ArrayList<E>();
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

package com.greentree.engine.moon.ecs.filter;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.Entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CopyOnWriteArrayListEntityCollection<E extends Entity> implements EntityCollection<E> {

    private final Iterable<? extends Comparator<? super E>> comparators;
    private final List<E> entities = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayListEntityCollection(Iterable<? extends Comparator<? super E>> comparators) {
        this.comparators = comparators;
    }

    public CopyOnWriteArrayListEntityCollection() {
        this(IteratorUtil.empty());
    }

    @Override
    public Iterator<E> iterator() {
        return entities.iterator();
    }

    @Override
    public boolean add(E entity) {
        if (!entities.contains(entity)) {
            final var result = entities.add(entity);
            for (var c : comparators)
                Collections.sort(entities, c);
            return result;
        }
        return false;
    }

    @Override
    public boolean remove(E entity) {
        return entities.remove(entity);
    }

    @Override
    public EntityCollection sort(Comparator<? super E> comparator) {
        return new CopyOnWriteArrayListEntityCollection(IteratorUtil.union(IteratorUtil.iterable(comparator), comparators));
    }

}

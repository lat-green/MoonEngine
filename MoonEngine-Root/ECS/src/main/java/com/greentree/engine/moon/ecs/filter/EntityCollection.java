package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.Entity;

import java.util.Comparator;

public interface EntityCollection<E extends Entity> extends Iterable<E> {

    boolean add(E entity);

    boolean remove(E entity);

    EntityCollection sort(Comparator<? super E> comparator);

}

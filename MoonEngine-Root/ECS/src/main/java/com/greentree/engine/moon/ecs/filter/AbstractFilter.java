package com.greentree.engine.moon.ecs.filter;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public abstract class AbstractFilter<E extends Entity> implements Filter<E> {

    private final Collection<ListenerCloser> lcs = new ArrayList<>();
    private final World world;
    private EntityCollection<E> filteredEntities;

    public AbstractFilter(World world, EntityCollection<E> filteredEntities) {
        this.world = world;
        this.filteredEntities = filteredEntities;
    }

    public AbstractFilter(World world) {
        this(world, new CopyOnWriteArrayListEntityCollection());
    }

    @Override
    public void close() {
        for (var lc : lcs)
            lc.close();
    }

    @Override
    public Iterator<E> iterator() {
        return filteredEntities.iterator();
    }

    @Override
    public Filter sort(Comparator<? super E> comparator) {
        filteredEntities = filteredEntities.sort(comparator);
        return this;
    }

    protected boolean addFilteredEntity(E e) {
        return filteredEntities.add(e);
    }

    protected void lc(ListenerCloser lc) {
        lcs.add(lc);
    }

    protected boolean removeFilteredEntity(E e) {
        return filteredEntities.remove(e);
    }

}

package com.greentree.common.ecs.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;
import com.greentree.commons.action.ListenerCloser;


public abstract class AbstractFilter implements Filter {

	private final Collection<ListenerCloser> lcs = new ArrayList<>();
	private EntityCollection filteredEntities;
	private final World world;

	public AbstractFilter(World world, EntityCollection filteredEntities) {
		this.world = world;
		this.filteredEntities = filteredEntities;
	}
	
	public AbstractFilter(World world) {
		this(world, new CopyOnWriteArrayListEntityCollection());
	}

	@Override
	public void close() {
		for(var lc : lcs)
			lc.close();
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public Iterator<Entity> iterator() {
		return filteredEntities.iterator();
	}

	@Override
	public Filter sort(Comparator<? super Entity> comparator) {
		filteredEntities = filteredEntities.sort(comparator);
		return this;
	}
	
	protected boolean addFilteredEntity(Entity e) {
		return filteredEntities.add(e);
	}

	protected void lc(ListenerCloser lc) {
		lcs.add(lc);
	}

	protected boolean removeFilteredEntity(Entity e) {
		return filteredEntities.remove(e);
	}

}

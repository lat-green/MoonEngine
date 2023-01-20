package com.greentree.common.ecs.filter;

import java.util.Iterator;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;


public class AllEntityFilter implements Filter {

	private final World world;
	
	public AllEntityFilter(World world) {
		this.world = world;
	}

	@Override
	public Iterator<Entity> iterator() {
		return world.iterator();
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public void close() {
	}
	
}

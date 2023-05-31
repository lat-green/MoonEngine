package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;

import java.util.Iterator;

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
    public void close() {
    }

}

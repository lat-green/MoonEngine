package com.greentree.engine.moon.ecs.system.container;

import com.greentree.commons.injector.InjectionContainer;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldComponent;


public class WorldInjectionContainer implements InjectionContainer {
	
	private final World world;
	
	public WorldInjectionContainer(World world) {
		this.world = world;
	}
	
	@Override
	public Object get(String name) {
		throw new UnsupportedOperationException("name: " + name);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> cls) {
		if(World.class == cls)
			return (T) world;
		var pcls = (Class<? extends WorldComponent>) cls;
		return (T) world.get(pcls);
	}
	
	@Override
	public <T> T get(String name, Class<T> cls) {
		return get(cls);
	}
	
}

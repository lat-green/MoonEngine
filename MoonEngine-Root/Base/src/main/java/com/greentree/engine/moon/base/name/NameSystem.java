package com.greentree.engine.moon.base.name;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.systems.CreateWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class NameSystem implements InitSystem, DestroySystem {
	
	
	private Names layers;
	private ListenerCloser lc1, lc2;
	
	@CreateWorldComponent({Names.class})
	@Override
	public void init(World world) {
		this.layers = new Names();
		world.add(layers);
		for(var e : world)
			if(e.contains(Name.class))
				layers.add(e);
		this.lc1 = world.onAddComponent(Name.class, e->layers.add(e));
		this.lc2 = world.onRemoveComponent(Name.class, e->layers.remove(e));
	}
	
	@Override
	public void destroy() {
		lc1.close();
		lc2.close();
		layers = null;
		lc1 = null;
		lc2 = null;
	}
	
}

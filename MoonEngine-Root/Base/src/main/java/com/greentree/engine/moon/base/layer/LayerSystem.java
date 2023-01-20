package com.greentree.engine.moon.base.layer;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.CreateWorldComponent;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.commons.action.ListenerCloser;

public class LayerSystem implements InitSystem, DestroySystem {
	
	
	private Layers layers;
	private ListenerCloser lc1, lc2;
	
	@CreateWorldComponent({Layers.class})
	@Override
	public void init(World world) {
		this.layers = new Layers();
		world.add(layers);
		for(var e : world)
			if(e.contains(Layer.class))
				layers.add(e);
		this.lc1 = world.onAddComponent(Layer.class, e->layers.add(e));
		this.lc2 = world.onRemoveComponent(Layer.class, e->layers.remove(e));
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

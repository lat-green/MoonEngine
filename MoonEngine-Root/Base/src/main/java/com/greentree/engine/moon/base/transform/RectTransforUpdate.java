package com.greentree.engine.moon.base.transform;

import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class RectTransforUpdate implements InitSystem, DestroySystem, UpdateSystem {
	
	private static final FilterBuilder builder = new FilterBuilder().required(RectTransform.class);
	private Filter filter;
	
	@WriteComponent({Transform.class})
	@ReadComponent(RectTransform.class)
	@Override
	public void update() {
		for(var e : filter) {
			final var rt = e.get(RectTransform.class);
			final var tr = e.get(Transform.class);
			
			final var c = rt.center();
			final var x = rt.xSize();
			final var y = rt.ySize();
			
			tr.position.set(c.x(), c.y(), 0);
			tr.rotation.identity();
			tr.scale.set(x, y, 1);
		}
	}
	
	@Override
	public void destroy() {
		filter.close();
		filter = null;
	}
	
	@Override
	public void init(World world) {
		filter = builder.build(world);
	}
	
}

package com.greentree.engine.moon.script;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.filter.Filter;
import com.greentree.common.ecs.filter.FilterBuilder;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.common.ecs.system.UpdateSystem;

public final class ScriptSystem implements InitSystem, DestroySystem, UpdateSystem {
	
	private static final FilterBuilder SCRIPTS = new FilterBuilder().required(Scripts.class);
	
	private Filter scripts;
	
	private World world;
	
	@Override
	public void update() {
		for(var entity : scripts) {
			final var scripts = entity.get(Scripts.class).scripts();
			for(var value : scripts) {
				final var s = value.get();
				
				for(var c : world.components())
					s.setConst(c.getClass().getSimpleName(), c);
				for(var c : entity)
					s.set(c.getClass().getSimpleName(), c);
				
				try {
					s.run();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void destroy() {
		scripts.close();
		scripts = null;
		world = null;
	}
	
	@Override
	public void init(World world) {
		this.world = world;
		scripts = SCRIPTS.build(world);
	}
	
}

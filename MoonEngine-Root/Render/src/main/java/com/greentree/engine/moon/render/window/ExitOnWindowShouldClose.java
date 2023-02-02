package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.annotation.WriteWorldComponent;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.module.ExitManager;
import com.greentree.engine.moon.module.ExitManagerProperty;

public class ExitOnWindowShouldClose implements InitSystem, DestroySystem, UpdateSystem {
	
	private Window window;
	private ExitManager manager;
	
	@Override
	public void destroy() {
		window = null;
		manager = null;
	}
	
	@WriteWorldComponent({EnginePropertiesWorldComponent.class})
	@ReadWorldComponent({WindowProperty.class})
	@Override
	public void init(World world) {
		final var properties = world.get(EnginePropertiesWorldComponent.class).properties();
		window = properties.get(WindowProperty.class).window();
		manager = properties.get(ExitManagerProperty.class).manager();
	}
	
	@WriteWorldComponent({EnginePropertiesWorldComponent.class})
	@Override
	public void update() {
		if(window.isShouldClose())
			manager.exit();
	}
	
}

package com.greentree.engine.moon.opengl;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.WriteWorldComponent;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.common.ecs.system.UpdateSystem;
import com.greentree.common.graphics.sgl.window.Window;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
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

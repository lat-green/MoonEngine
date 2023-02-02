package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;

public final class DisableCursor implements InitSystem, DestroySystem {
	
	private World world;
	private CursorInputMode cursorInputMode;
	
	@ReadWorldComponent({EnginePropertiesWorldComponent.class})
	@Override
	public void init(World world) {
		this.world = world;
		final var window = world.get(WindowProperty.class).window();
		
		this.cursorInputMode = window.getInputMode();
		window.setInputMode(CursorInputMode.DISABLED);
	}
	
	@ReadWorldComponent({EnginePropertiesWorldComponent.class})
	@Override
	public void destroy() {
		final var window = world.get(WindowProperty.class).window();
		window.setInputMode(cursorInputMode);
		
		cursorInputMode = null;
		world = null;
	}
	
}

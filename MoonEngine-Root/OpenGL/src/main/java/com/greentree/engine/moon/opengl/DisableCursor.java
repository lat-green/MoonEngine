package com.greentree.engine.moon.opengl;

import org.lwjgl.glfw.GLFW;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.ReadWorldComponent;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;

public final class DisableCursor implements InitSystem, DestroySystem {
	
	private World world;
	private int cursorInputMode;
	
	@ReadWorldComponent({EnginePropertiesWorldComponent.class})
	@Override
	public void init(World world) {
		this.world = world;
		final var window = world.get(EnginePropertiesWorldComponent.class).properties().get(WindowProperty.class).window();
		this.cursorInputMode = GLFW.glfwGetInputMode(window.glID, GLFW.GLFW_CURSOR);
		GLFW.glfwSetInputMode(window.glID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	@ReadWorldComponent({EnginePropertiesWorldComponent.class})
	@Override
	public void destroy() {
		final var window = world.get(EnginePropertiesWorldComponent.class).properties().get(WindowProperty.class).window();
		GLFW.glfwSetInputMode(window.glID, GLFW.GLFW_CURSOR, cursorInputMode);
		
		cursorInputMode = -1;
		world = null;
	}
	
}

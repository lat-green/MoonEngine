package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.window.GLFWContext;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class GLFWUpdateEvents implements UpdateSystem {
	
	@Override
	public void update() {
		GLFWContext.updateEvents();
	}
	
}

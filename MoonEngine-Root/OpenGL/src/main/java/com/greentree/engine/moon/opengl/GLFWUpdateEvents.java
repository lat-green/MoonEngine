package com.greentree.engine.moon.opengl;

import com.greentree.common.ecs.system.UpdateSystem;
import com.greentree.common.graphics.sgl.window.GLFWContext;

public class GLFWUpdateEvents implements UpdateSystem {
	
	@Override
	public void update() {
		GLFWContext.updateEvents();
	}
	
}

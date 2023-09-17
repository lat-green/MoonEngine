
package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.Window;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.modules.UpdateModule;

public class WindowUpdateEvents implements UpdateModule {
	
	@Override
	public void update() {
		Window.updateEvents();
	}
	
}

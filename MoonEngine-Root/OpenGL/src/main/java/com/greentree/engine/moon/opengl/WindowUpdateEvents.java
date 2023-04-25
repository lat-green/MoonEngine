
package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.Window;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class WindowUpdateEvents implements UpdateSystem {
	
	@Override
	public void update() {
		Window.updateEvents();
	}
	
}

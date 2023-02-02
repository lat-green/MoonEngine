package com.greentree.engine.moon.opengl.adapter;

import com.greentree.engine.moon.render.window.Window;
import com.greentree.engine.moon.render.window.WindowLibrary;

public final class GLFWWindowLibrary implements WindowLibrary {
	
	@Override
	public Window createWindow(String title, int width, int height) {
		return new WindowAddapter(
				new com.greentree.common.graphics.sgl.window.Window(title, width, height));
	}
	
	@Override
	public void updateEvents() {
		com.greentree.common.graphics.sgl.window.Window.updateEvents();
	}
	
	
	
}

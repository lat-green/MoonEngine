package com.greentree.engine.moon.render.window;


public interface WindowLibrary {
	
	Window createWindow(String title, int width, int height);
	
	void updateEvents();
	
}

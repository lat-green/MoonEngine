package com.greentree.engine.moon.render.window;


public interface Window {
	
	int getHeight();
	int getWidth();
	
	String getTitle();
	
	
	void swapBuffer();
	boolean isShouldClose();
	void setInputMode(CursorInputMode mode);
	CursorInputMode getInputMode();
	
	
}

package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.render.pipeline.target.RenderTarget;

public interface Window {
	
	int getHeight();
	int getWidth();
	
	String getTitle();
	
	RenderTarget screanRenderTarget();
	
	void swapBuffer();
	boolean isShouldClose();
	void setInputMode(CursorInputMode mode);
	CursorInputMode getInputMode();
	
	
}

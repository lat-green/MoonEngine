package com.greentree.engine.moon.opengl.render.material;

import com.greentree.engine.moon.render.material.PropertyContext;

public final class OpenGLPropertyContext implements PropertyContext {
	
	private int next;
	
	public OpenGLPropertyContext() {
	}
	
	public OpenGLPropertyContext(int next) {
		this.next = next;
	}
	
	@Override
	public int nextEmptyTexture() {
		return next++;
	}
	
}

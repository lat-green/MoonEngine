package com.greentree.engine.moon.opengl.adapter;

import com.greentree.engine.moon.render.material.MaterialProperties;

public interface Shader {
	
	void bind();
	
	void unbind();
	
	void set(MaterialProperties p);
	
	default void set(MaterialProperties p, MaterialProperties last) {
		set(p);
	}
	
}

package com.greentree.engine.moon.render.material;

import java.io.Serializable;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface Property extends Serializable {
	
	void set(UniformLocation location, PropertyContext context);
	
}

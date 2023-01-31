package com.greentree.engine.moon.render.material;

import org.joml.Matrix4f;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface Matrix4fProperty extends Property {
	
	Matrix4f value();
	
	@Override
	default void set(UniformLocation location, PropertyContext context) {
		location.set4fv(value());
	}
	
}

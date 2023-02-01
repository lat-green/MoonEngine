package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface Vector3fProperty extends MaterialProperty {
	
	float x();
	float y();
	float z();
	
	@Override
	default void set(UniformLocation location) {
		location.setFloat(x(), y(), z());
	}
	
}

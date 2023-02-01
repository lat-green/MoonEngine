package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface FloatProperty extends MaterialProperty {
	
	float value();
	
	@Override
	default void set(UniformLocation location) {
		location.setFloat(value());
	}
	
}

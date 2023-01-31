package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface FloatProperty extends Property {
	
	float value();
	
	@Override
	default void set(UniformLocation location, PropertyContext context) {
		location.setf(value());
	}
	
}

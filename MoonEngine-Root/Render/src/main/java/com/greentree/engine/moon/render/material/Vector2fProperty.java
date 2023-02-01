package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface Vector2fProperty extends MaterialProperty {
	
	float x();
	float y();
	
	
	@Override
	default void set(UniformLocation location) {
		location.setFloat(x(), y());
	}
	
}

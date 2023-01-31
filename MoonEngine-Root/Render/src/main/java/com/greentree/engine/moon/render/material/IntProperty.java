package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface IntProperty extends Property {
	
	int value();
	
	@Override
	default void set(UniformLocation location, PropertyContext context) {
		location.seti(value());
	}
	
}

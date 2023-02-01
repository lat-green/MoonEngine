package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface IntProperty extends MaterialProperty {
	
	int offset();
	int value();
	
	@Override
	default void set(UniformLocation location) {
		location.setOffsetInt(offset(), value());
	}
	
}

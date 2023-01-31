package com.greentree.engine.moon.render.material;

import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.render.shader.UniformLocation;

public interface Vector3fProperty extends Property {
	
	AbstractVector3f value();
	
	@Override
	default void set(UniformLocation location, PropertyContext context) {
		location.setf(value());
	}
	
}

package com.greentree.engine.moon.render.material;

import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector3f;

public record Vector3fPropertyImpl(AbstractVector3f value) implements Vector3fProperty {
	
	public Vector3fPropertyImpl {
		Objects.requireNonNull(value);
	}
	
	public Vector3fPropertyImpl(Color color) {
		this(color.r, color.g, color.b);
	}
	
	public Vector3fPropertyImpl(float x, float y, float z) {
		this(new Vector3f(x, y, z));
	}
	
}

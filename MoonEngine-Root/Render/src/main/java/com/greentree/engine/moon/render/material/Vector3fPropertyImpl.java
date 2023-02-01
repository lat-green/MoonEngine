package com.greentree.engine.moon.render.material;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector3f;

public record Vector3fPropertyImpl(float x, float y, float z) implements Vector3fProperty {
	
	public Vector3fPropertyImpl {
	}
	
	public Vector3fPropertyImpl(Color color) {
		this(color.r, color.g, color.b);
	}
	
	public Vector3fPropertyImpl(AbstractVector3f vector) {
		this(vector.x(), vector.y(), vector.z());
	}
	
}

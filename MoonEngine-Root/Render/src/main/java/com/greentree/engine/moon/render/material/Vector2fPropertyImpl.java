package com.greentree.engine.moon.render.material;

import com.greentree.commons.math.vector.AbstractVector2f;

public record Vector2fPropertyImpl(float x, float y) implements Vector2fProperty {
	
	public Vector2fPropertyImpl {
	}
	
	public Vector2fPropertyImpl(AbstractVector2f vector) {
		this(vector.x(), vector.y());
	}
	
}

package com.greentree.engine.moon.render.material;

import org.joml.Matrix4f;

public record Matrix4fPropertyImpl(int offset, Matrix4f value) implements Matrix4fProperty {
	
	
	public Matrix4fPropertyImpl(Matrix4f value) {
		this(0, value);
	}
	
	@Override
	public MaterialProperty offset(int offset) {
		return new Matrix4fPropertyImpl(offset + this.offset, value);
	}
	
}

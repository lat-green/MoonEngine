package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.UniformLocation;


public record EmptyMaterialProperty() implements MaterialProperty {
	
	@Override
	public void set(UniformLocation location) {
	}
	
	@Override
	public MaterialProperty offset(int offset) {
		return this;
	}
	
}

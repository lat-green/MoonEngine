package com.greentree.engine.moon.render.material;

public record FloatPropertyImpl(int offset, float value) implements FloatProperty {
	
	
	public FloatPropertyImpl(float value) {
		this(0, value);
	}
	
	@Override
	public MaterialProperty offset(int offset) {
		return new FloatPropertyImpl(offset + this.offset, value);
	}
}

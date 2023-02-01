package com.greentree.engine.moon.render.material;

public record IntPropertyImpl(int offset, int value) implements IntProperty {
	
	public IntPropertyImpl(int value) {
		this(0, value);
	}
	
	@Override
	public MaterialProperty offset(int offset) {
		return new IntPropertyImpl(offset + this.offset, value);
	}
	
}

package com.greentree.engine.moon.render.material;


public record float1Property(float x) implements Property {
	
	@Override
	public void bind(PropertyLocation property) {
		property.setFloat(x);
	}
	
	@Override
	public String toString() {
		return "[" + x + "]";
	}
	
}

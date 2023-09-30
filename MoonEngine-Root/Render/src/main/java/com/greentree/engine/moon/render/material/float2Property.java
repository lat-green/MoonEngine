package com.greentree.engine.moon.render.material;


public record float2Property(float x, float y) implements Property {
	
	@Override
	public void bind(PropertyLocation property) {
		property.setFloat(x, y);
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}

package com.greentree.engine.moon.render.material;


public record float3Property(float x, float y, float z) implements Property {
	
	@Override
	public void bind(PropertyLocation property) {
		property.setFloat(x, y, z);
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
	
}

package com.greentree.engine.moon.render.pipeline.material;


public record float1Property(float x) implements Property {
	
	@Override
	public void bind(PropertyLocation property, PropertyBindContext context) {
		property.setFloat(x);
	}
	
	@Override
	public String toString() {
		return "[" + x + "]";
	}
	
}

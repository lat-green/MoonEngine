package com.greentree.engine.moon.render.pipeline.material;


public record int1Property(int x) implements Property {
	
	@Override
	public void bind(PropertyLocation property, PropertyBindContext context) {
		property.setInt(x);
	}
	
}

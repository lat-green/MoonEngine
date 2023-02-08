package com.greentree.engine.moon.render.pipeline.material;


public record float3Property(float x, float y, float z) implements Property {
	
	@Override
	public void bind(PropertyLocation property, PropertyBindContext context) {
		property.setFloat(x, y, z);
	}
	
}

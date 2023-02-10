package com.greentree.engine.moon.render.pipeline.material;


public record mat4Property(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13,
		float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) implements Property {
	
	@Override
	public void bind(PropertyLocation property, PropertyBindContext context) {
		property.setMatrix4x4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}
	
}
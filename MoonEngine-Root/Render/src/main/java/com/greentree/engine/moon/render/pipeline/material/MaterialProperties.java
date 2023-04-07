package com.greentree.engine.moon.render.pipeline.material;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;

public interface MaterialProperties extends Iterable<String> {
	
	default MaterialProperties newChildren() {
		return new MaterialPropertiesWithParent(this);
	}
	
	Property get(String name);
	
	default void put(String name, AbstractVector2f vector) {
		put(name, vector.x(), vector.y());
	}
	
	default void put(String name, AbstractVector3f vector) {
		put(name, vector.x(), vector.y(), vector.z());
	}
	
	default void put(String name, float x) {
		put(name, new float1Property(x));
	}
	
	default void put(String name, float x, float y) {
		put(name, new float2Property(x, y));
	}
	
	default void put(String name, float x, float y, float z) {
		put(name, new float3Property(x, y, z));
	}
	
	default void put(String name, float m00, float m01, float m02, float m03, float m10, float m11, float m12,
			float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
		put(name, new mat4Property(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33));
	}
	
	default void put(String name, int x) {
		put(name, new int1Property(x));
	}
	
	
	default void put(String name, Matrix4f matrix) {
		put(name, matrix.m00(), matrix.m01(), matrix.m02(), matrix.m03(), matrix.m10(), matrix.m11(), matrix.m12(),
				matrix.m13(), matrix.m20(), matrix.m21(), matrix.m22(), matrix.m23(), matrix.m30(), matrix.m31(),
				matrix.m32(), matrix.m33());
	}
	
	
	void put(String name, Property property);
	
	
	default void putRGB(String name, Color color) {
		put(name, color.r, color.g, color.b);
	}
	
}

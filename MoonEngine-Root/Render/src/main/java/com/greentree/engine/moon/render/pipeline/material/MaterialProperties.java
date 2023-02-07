package com.greentree.engine.moon.render.pipeline.material;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.render.texture.Texture;

public interface MaterialProperties {
	
	void set(Shader shader);
	
	void put(String name, float m00, float m01, float m02, float m03, float m10, float m11,
			float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31,
			float m32, float m33);
	
	default void put(String name, Matrix4f matrix) {
		put(name, matrix.m00(), matrix.m01(), matrix.m02(), matrix.m03(), matrix.m10(),
				matrix.m11(), matrix.m12(), matrix.m13(), matrix.m20(), matrix.m21(), matrix.m22(),
				matrix.m23(), matrix.m30(), matrix.m31(), matrix.m32(), matrix.m33());
	}
	
	void put(String name, float x);
	
	void put(String name, float x, float y);
	
	void put(String name, float x, float y, float z);
	
	void put(String name, int x);
	
	default void putRGB(String name, Color color) {
		put(name, color.r, color.g, color.b);
	}
	
	default void put(String name, AbstractVector3f vector) {
		put(name, vector.x(), vector.y(), vector.z());
	}
	
	default void put(String name, AbstractVector2f vector) {
		put(name, vector.x(), vector.y());
	}
	
	void put(String name, Texture texture);
	
	MaterialProperties diff(MaterialProperties other);
	
}

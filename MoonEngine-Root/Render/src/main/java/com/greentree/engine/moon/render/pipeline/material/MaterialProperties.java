package com.greentree.engine.moon.render.pipeline.material;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;

public interface MaterialProperties {
	
	void set(Shader shader);
	
	MaterialProperty get(String name);
	
	default void put(String name, Matrix4f matrix) {
		get(name).setMatrix4x4f(matrix);
	}
	
	default void put(String name, float x) {
		get(name).setFloat(x);
	}
	
	default void put(String name, float x, float y) {
		get(name).setFloat(x, y);
	}
	
	default void put(String name, float x, float y, float z) {
		get(name).setFloat(x, y, z);
	}
	
	default void put(String name, int x) {
		get(name).setInt(x);
	}
	
	default void putRGB(String name, Color color) {
		get(name).setFloat(color);
	}
	
	default void put(String name, AbstractVector3f vector) {
		get(name).setFloat(vector);
	}
	
	default void put(String name, AbstractVector2f vector) {
		get(name).setFloat(vector);
	}
	
}

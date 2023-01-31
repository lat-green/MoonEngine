package com.greentree.engine.moon.render.material;

import java.io.Serializable;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector3f;

public interface MaterialProperties extends Serializable {
	
	void put(String name, Property property);
	Property remove(String name);
	Property get(String name);
	Iterable<? extends String> getNames();
	boolean has(String name);
	
	
	default void put(String name, AbstractVector3f vector) {
		put(name, new Vector3fPropertyImpl(vector));
	}
	
	default void putRGB(String name, Color color) {
		put(name, new Vector3fPropertyImpl(color));
	}
	
	default void put(String name, int n) {
		put(name, new IntPropertyImpl(n));
	}
	
	default void put(String name, float x) {
		put(name, new FloatPropertyImpl(x));
	}
	
	default void put(String name, Matrix4f matrix) {
		put(name, new Matrix4fPropertyImpl(matrix));
	}
	
}

package com.greentree.engine.moon.render.pipeline.material;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.render.texture.Texture;

public abstract class ProxyMaterialProperties implements MaterialProperties {
	
	protected final MaterialProperties base;
	
	public ProxyMaterialProperties(MaterialProperties base) {
		this.base = base;
	}
	
	@Override
	public void set(Shader shader) {
		base.set(shader);
	}
	
	
	@Override
	public void put(String name, float m00, float m01, float m02, float m03, float m10, float m11,
			float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31,
			float m32, float m33) {
		base.put(name, m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32,
				m33);
	}
	
	
	@Override
	public void put(String name, float x) {
		base.put(name, x);
	}
	
	
	@Override
	public void put(String name, float x, float y) {
		base.put(name, x, y);
	}
	
	
	@Override
	public void put(String name, float x, float y, float z) {
		base.put(name, x, y, z);
	}
	
	
	@Override
	public void put(String name, int x) {
		base.put(name, x);
	}
	
	
	@Override
	public void put(String name, Texture texture) {
		base.put(name, texture);
	}
	
	
	@Override
	public MaterialProperties diff(MaterialProperties other) {
		return base.diff(other);
	}
	
	@Override
	public void put(String name, AbstractVector2f vector) {
		base.put(name, vector);
	}
	
	
	@Override
	public void put(String name, AbstractVector3f vector) {
		base.put(name, vector);
	}
	
	@Override
	public void put(String name, Matrix4f matrix) {
		base.put(name, matrix);
	}
	
	@Override
	public void putRGB(String name, Color color) {
		base.putRGB(name, color);
	}
	
}

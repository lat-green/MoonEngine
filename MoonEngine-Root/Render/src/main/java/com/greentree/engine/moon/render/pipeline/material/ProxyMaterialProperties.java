package com.greentree.engine.moon.render.pipeline.material;

import java.util.Map;

import org.joml.Matrix4f;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;

public abstract class ProxyMaterialProperties implements MaterialProperties {
	
	protected final MaterialProperties base;
	
	public ProxyMaterialProperties(MaterialProperties base) {
		this.base = base;
	}
	
	@Override
	public MaterialProperty get(String name) {
		return base.get(name);
	}
	
	@Override
	public Map<? extends String, ? extends Integer> ints() {
		return base.ints();
	}
	
	@Override
	public Map<? extends String, ? extends Float> floats() {
		return base.floats();
	}
	
	@Override
	public Map<? extends String, ? extends AbstractVector2f> vec2s() {
		return base.vec2s();
	}
	
	@Override
	public Map<? extends String, ? extends AbstractVector3f> vec3s() {
		return base.vec3s();
	}
	
	@Override
	public Map<? extends String, ? extends Matrix4f> mat4s() {
		return base.mat4s();
	}
	
}

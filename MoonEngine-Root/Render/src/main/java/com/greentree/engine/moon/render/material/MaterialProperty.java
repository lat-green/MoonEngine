package com.greentree.engine.moon.render.material;

import java.io.Serializable;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface MaterialProperty extends Serializable {
	
	void set(UniformLocation location);
	
	default MaterialProperty offset(int offset) {
		return OffsetMaterialProperty.offset(this, offset);
	}
	
}

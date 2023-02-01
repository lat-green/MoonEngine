package com.greentree.engine.moon.render.material;

import org.joml.Matrix4f;

import com.greentree.engine.moon.render.shader.UniformLocation;

public interface Matrix4fProperty extends MaterialProperty {
	
	int offset();
	@Override
	default void set(UniformLocation location) {
		final var m = value();
		location.setOffsetMatrix4x4f(offset(),
				m.m00(), m.m01(), m.m02(), m.m03(),
				m.m10(), m.m11(), m.m12(), m.m13(),
				m.m20(), m.m21(), m.m22(), m.m23(),
				m.m30(), m.m31(), m.m32(), m.m33());
	}
	
	Matrix4f value();
	
}

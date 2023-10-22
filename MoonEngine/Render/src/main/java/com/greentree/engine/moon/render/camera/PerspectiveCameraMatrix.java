package com.greentree.engine.moon.render.camera;

import java.io.ObjectStreamException;

import org.joml.Matrix4f;

import com.greentree.commons.math.Mathf;


public final class PerspectiveCameraMatrix implements ProjectionMatrix {
	
	private static final long serialVersionUID = 1L;
	
	public static final PerspectiveCameraMatrix INSTANCE = new PerspectiveCameraMatrix();
	
	protected Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
	
	private PerspectiveCameraMatrix() {
	}
	
	@Override
	public Matrix4f getMatrix(float width, float height) {
		final var wh = width / height;
		return new Matrix4f().perspective(Mathf.atan(wh), wh, 0, 30);
	}
	
}

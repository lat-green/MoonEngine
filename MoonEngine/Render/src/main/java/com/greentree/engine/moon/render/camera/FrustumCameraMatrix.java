package com.greentree.engine.moon.render.camera;

import java.io.ObjectStreamException;

import org.joml.Matrix4f;


public final class FrustumCameraMatrix implements ProjectionMatrix {
	private static final long serialVersionUID = 1L;
	
	public static final FrustumCameraMatrix INSTANCE = new FrustumCameraMatrix();

	private FrustumCameraMatrix() {
	}

	protected Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
	
	@Override
	public Matrix4f getMatrix(float width, float height) {
		final float m = 0.001f;

		final float w = 1.0F * m;
		final float h = height / width * w;

		return new Matrix4f().frustum(-w, w, -h, h, m, 10000.0F);
	}

}

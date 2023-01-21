package com.greentree.engine.moon.render.camera;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record CameraComponent(int width, int height, Matrix4fc projection) implements ConstComponent {
	
	public CameraComponent(int width, int height) {
		this(width, height, FrustumCameraMatrix.INSTANCE);
	}
	
	public CameraComponent(int width, int height, ProjectionMatrix matrix) {
		this(width, height, matrix.getMatrix(width, height));
	}
	
	public Matrix4f getProjectionMatrix() {
		return getProjectionMatrix(new Matrix4f());
	}
	
	public Matrix4f getProjectionMatrix(Matrix4f dest) {
		return dest.set(projection);
	}
	
}

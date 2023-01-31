package com.greentree.engine.moon.render.camera;

import org.joml.Matrix4f;
import org.joml.Quaternionfc;

import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.Entity;

public class CameraUtil {
	
	public static Matrix4f getView(Entity camera) {
		if(camera == null)
			return new Matrix4f();
		final var t = camera.get(Transform.class);
		return getView(t);
	}
	
	public static Matrix4f getView(Transform camera) {
		if(camera == null)
			return new Matrix4f();
		return getView(camera.position, camera.rotation);
	}
	
	public static Matrix4f getView(AbstractVector3f position, Quaternionfc rotation) {
		return new Matrix4f().lookAt(position.toJoml(),
				Transform.direction(rotation).add(position).toJoml(), Transform.UP.toJoml());
	}
	
}

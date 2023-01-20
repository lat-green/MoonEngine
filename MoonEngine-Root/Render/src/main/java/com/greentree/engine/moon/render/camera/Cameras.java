package com.greentree.engine.moon.render.camera;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.WorldComponent;
import com.greentree.common.ecs.annotation.CreateSystem;
import com.greentree.engine.moon.base.transform.Transform;

@CreateSystem(CameraSystem.class)
public final class Cameras implements WorldComponent {
	
	private static final long serialVersionUID = 1L;
	
	private Entity mainCamera;
	
	public Entity main() {
		if(mainCamera == null) {
			System.err.println("create temp camera");
			final var c = new Entity();
			try(final var lock = c.lock()) {
				lock.add(new Transform());
				lock.add(new CameraComponent(1, 1, FrustumCameraMatrix.INSTANCE));
			}
			return c;
		}
		return mainCamera;
	}
	
	void setMainCamera(Entity mainCamera) {
		this.mainCamera = mainCamera;
	}
	
}

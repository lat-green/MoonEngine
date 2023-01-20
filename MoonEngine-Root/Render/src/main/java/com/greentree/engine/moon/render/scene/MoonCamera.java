package com.greentree.engine.moon.render.scene;

import org.joml.Matrix4f;

import com.greentree.common.ecs.Entity;
import com.greentree.common.renderer.camera.Camera;
import com.greentree.common.renderer.texture.CubeTextureData;
import com.greentree.engine.moon.render.camera.CameraComponent;
import com.greentree.engine.moon.render.camera.CameraUtil;

public record MoonCamera(Entity entity) implements Camera {
	
	@Override
	public Matrix4f getProjectionMatrix() {
		return entity.get(CameraComponent.class).getProjectionMatrix();
	}
	
	@Override
	public Matrix4f getViewMatrix() {
		return CameraUtil.getView(entity);
	}
	
	@Override
	public int getWidth() {
		return entity.get(CameraComponent.class).width();
	}
	
	@Override
	public int getHeight() {
		return entity.get(CameraComponent.class).height();
	}
	
	@Override
	public CubeTextureData getSkyBox() {
		if(entity.contains(SkyBoxComponent.class)) {
			return entity.get(SkyBoxComponent.class).texture.get();
		}
		return null;
	}
	
}

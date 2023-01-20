package com.greentree.engine.moon.render.scene;

import org.joml.Matrix4f;

import com.greentree.common.ecs.Entity;
import com.greentree.common.renderer.light.DirectionLight;
import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.render.camera.CameraUtil;
import com.greentree.engine.moon.render.light.HasShadow;
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent;


public record MoonDirectionLight(Entity entity) implements DirectionLight {
	
	@Override
	public Color color() {
		return entity.get(DirectionLightComponent.class).color();
	}
	
	@Override
	public float intensity() {
		return entity.get(DirectionLightComponent.class).intensity();
	}
	
	@Override
	public AbstractVector3f getPosition() {
		return entity.get(Transform.class).position;
	}
	
	@Override
	public AbstractVector3f direction() {
		return entity.get(Transform.class).direction();
	}
	
	@Override
	public boolean hasShadow() {
		return entity.contains(HasShadow.class);
	}
	
	@Override
	public Matrix4f getProjectionMatrix() {
		return entity.get(DirectionLightComponent.class).getProjectionMatrix();
	}
	
	@Override
	public Matrix4f getViewMatrix() {
		return CameraUtil.getView(entity);
	}
	
}

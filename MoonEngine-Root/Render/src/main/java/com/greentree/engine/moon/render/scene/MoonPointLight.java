package com.greentree.engine.moon.render.scene;

import com.greentree.common.ecs.Entity;
import com.greentree.common.renderer.light.PointLight;
import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.render.light.HasShadow;
import com.greentree.engine.moon.render.light.point.PointLightComponent;


public record MoonPointLight(Entity entity) implements PointLight {
	
	@Override
	public Color color() {
		return entity.get(PointLightComponent.class).color();
	}
	
	@Override
	public float intensity() {
		return entity.get(PointLightComponent.class).intensity();
	}
	
	@Override
	public AbstractVector3f position() {
		return entity.get(Transform.class).position;
	}
	
	@Override
	public boolean hasShadow() {
		return entity.contains(HasShadow.class);
	}
	
}

package com.greentree.engine.moon.render.light.direction;

import java.util.Objects;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record DirectionLightComponent(Color color, float intensity) implements ConstComponent {
	
	public DirectionLightComponent(float intensity) {
		this(Color.white, intensity);
	}
	
	public DirectionLightComponent() {
		this(1);
	}
	
	public DirectionLightComponent {
		Objects.requireNonNull(color);
	}
	
	
	public Matrix4f getProjectionMatrix() {
		final var size = 10;
		final var min = 1;
		final var max = 500;
		return new Matrix4f().ortho(-size, size, -size, size, min, max);
	}
	
}

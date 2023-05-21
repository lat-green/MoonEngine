package com.greentree.engine.moon.render.light.point;

import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record PointLightComponent(Color color, float intensity) implements ConstComponent {
	
	private static final Color COLOR = Color.white;
	
	private static final float INTENSITY = 1;
	
	public float maxDistanse() {//TODO
		return intensity * intensity;
	}
	
	public PointLightComponent() {
		this(COLOR, INTENSITY);
	}
	
	public PointLightComponent(Color color) {
		this(color, INTENSITY);
	}
	
	public PointLightComponent(float intensity) {
		this(COLOR, intensity);
	}
	
	public PointLightComponent {
		Objects.requireNonNull(color);
	}
	
}

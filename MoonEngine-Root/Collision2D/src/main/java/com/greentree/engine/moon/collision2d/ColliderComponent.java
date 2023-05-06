package com.greentree.engine.moon.collision2d;

import com.greentree.engine.moon.ecs.component.Component;

public record ColliderComponent(Collider collider) implements Component {
	
	@Override
	public Component copy() {
		return null;
	}
	
}

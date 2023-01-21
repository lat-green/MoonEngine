package com.greentree.engine.moon.base.layer;

import java.util.Objects;

import com.greentree.engine.moon.ecs.component.ConstComponent;

public record Layer(String value) implements ConstComponent {
	
	
	public Layer {
		Objects.requireNonNull(value);
	}
	
	@Override
	public String toString() {
		return "Layer [" + value + "]";
	}
	
}

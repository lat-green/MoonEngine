package com.greentree.engine.moon.base.name;

import java.util.Objects;

import com.greentree.common.ecs.component.ConstComponent;

public record Name(String value) implements ConstComponent {
	
	
	public Name {
		Objects.requireNonNull(value);
	}
	
	@Override
	public String toString() {
		return "Name [" + value + "]";
	}
	
}

package com.greentree.engine.moon.render.shader;

import java.util.Objects;

@Deprecated
public record MaterialField(PropertyType type, String name) {
	
	public MaterialField {
		Objects.requireNonNull(type);
		Objects.requireNonNull(name);
	}
	
}

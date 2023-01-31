package com.greentree.engine.moon.render.material;

import java.util.Objects;

import com.greentree.engine.moon.render.shader.ShaderProgram;

public record Material(MaterialProperties properties, ShaderProgram shader) {
	
	
	public Material {
		Objects.requireNonNull(properties);
		Objects.requireNonNull(shader);
	}
	
	public Material(ShaderProgram shader) {
		this(new MaterialPropertiesImpl(), shader);
	}
	
	public void set(PropertyContext context) {
		for(var n : properties.getNames()) {
			final var p = properties.get(n);
			final var location = shader.getUL(n);
			p.set(location, context);
		}
	}
}

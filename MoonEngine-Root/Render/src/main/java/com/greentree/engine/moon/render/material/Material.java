package com.greentree.engine.moon.render.material;

import java.util.Objects;

import com.greentree.engine.moon.render.pipeline.source.buffer.SourceCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderProgram;

public record Material(MaterialProperties properties, ShaderProgram shader) {
	
	
	public Material {
		Objects.requireNonNull(properties);
		Objects.requireNonNull(shader);
	}
	
	public Material(ShaderProgram shader) {
		this(new MaterialPropertiesImpl(), shader);
	}
	
	
	public SourceCommandBuffer buffer() {
		return shader.buffer(properties);
	}
	
}

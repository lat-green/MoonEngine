package com.greentree.engine.moon.render.pipeline.material;

public record Material(Shader shader, MaterialProperties properties) {
	
	public ShaderCommandBuffer buffer() {
		properties.set(shader);
		return shader.buffer();
	}
	
}

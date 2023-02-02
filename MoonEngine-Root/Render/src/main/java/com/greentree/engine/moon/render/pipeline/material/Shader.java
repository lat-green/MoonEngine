package com.greentree.engine.moon.render.pipeline.material;

public interface Shader {
	
	Iterable<? extends String> getPropertyNames();
	MaterialProperty getProperty(String name);
	ShaderCommandBuffer buffer();
	
}

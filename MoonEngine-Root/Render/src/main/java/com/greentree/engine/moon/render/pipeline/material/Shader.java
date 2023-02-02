package com.greentree.engine.moon.render.pipeline.material;

public interface Shader {
	
	Iterable<? extends String> getPropertyNames();
	PropertyLocation getProperty(String name);
	void bind();
	void unbind();
	
}

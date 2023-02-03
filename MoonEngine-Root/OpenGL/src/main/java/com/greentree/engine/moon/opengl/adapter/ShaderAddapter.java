package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.engine.moon.render.pipeline.material.PropertyLocation;
import com.greentree.engine.moon.render.pipeline.material.Shader;


public record ShaderAddapter(GLShaderProgram program) implements Shader {
	
	@Override
	public Iterable<? extends String> getPropertyNames() {
		return program.getUniformLocationNames();
	}
	
	@Override
	public PropertyLocation getProperty(String name) {
		final var location = program.getUL(name);
		return new MaterialPropertyAddapter(location);
	}
	
	@Override
	public void bind() {
		program.bind();
	}
	
	@Override
	public void unbind() {
		program.unbind();
	}
	
}

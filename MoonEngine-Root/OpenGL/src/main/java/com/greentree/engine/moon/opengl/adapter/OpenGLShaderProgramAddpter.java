package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.material.ShaderCommandBuffer;


public record OpenGLShaderProgramAddpter(GLShaderProgram program) implements Shader {
	
	@Override
	public ShaderCommandBuffer buffer(MaterialProperties properties) {
		return null;
	}
	
}

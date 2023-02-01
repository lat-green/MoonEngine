package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.source.buffer.SourceCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderProgram;


public record OpenGLShaderProgramAddpter(GLShaderProgram program) implements ShaderProgram {
	
	@Override
	public SourceCommandBuffer buffer(MaterialProperties properties) {
		return null;
	}
	
}

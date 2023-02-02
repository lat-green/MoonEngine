package com.greentree.engine.moon.opengl.command;

import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;

public record DrawSkyBox(Shader material, MaterialProperties properties) implements OpenGLCommand {
	
	@Override
	public void run(OpenGLContext context) {
		
	}
	
}

package com.greentree.engine.moon.render.pipeline.material;

import com.greentree.engine.moon.render.shader.ShaderProgramData;

public record Material(ShaderProgramData shader, MaterialProperties properties) {
	
}

package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.ShaderProgramData;

public record Material(ShaderProgramData shader, MaterialProperties properties) {
	
}

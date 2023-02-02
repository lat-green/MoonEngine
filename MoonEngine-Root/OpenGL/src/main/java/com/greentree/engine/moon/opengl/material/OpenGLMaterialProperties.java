package com.greentree.engine.moon.opengl.material;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;

public interface OpenGLMaterialProperties extends MaterialProperties {
	
	@Override
	OpenGLMaterialProperty get(String name);
	
	default void put(String name, GLTexture texture) {
		get(name).setTexture(texture);
	}
	
}

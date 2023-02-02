package com.greentree.engine.moon.opengl.material;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperty;

public interface OpenGLMaterialProperty extends MaterialProperty {
	
	void setTexture(GLTexture texture);
	
}

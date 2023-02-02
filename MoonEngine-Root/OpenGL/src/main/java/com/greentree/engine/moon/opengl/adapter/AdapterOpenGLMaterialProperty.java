package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.opengl.material.OpenGLMaterialProperty;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperty;
import com.greentree.engine.moon.render.pipeline.material.ProxyMaterialProperty;


public class AdapterOpenGLMaterialProperty extends ProxyMaterialProperty
		implements OpenGLMaterialProperty {
	
	
	private AdapterOpenGLMaterialProperty(MaterialProperty base) {
		super(base);
	}
	
	
	@Override
	public void setTexture(GLTexture texture) {
		throw new UnsupportedOperationException();
	}
	
	
	public static OpenGLMaterialProperty create(MaterialProperty property) {
		if(property instanceof OpenGLMaterialProperty p)
			return p;
		return new AdapterOpenGLMaterialProperty(property);
	}
	
}

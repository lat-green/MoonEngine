package com.greentree.engine.moon.opengl.material;

import java.util.HashMap;
import java.util.Map;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.opengl.adapter.AdapterOpenGLMaterialProperty;
import com.greentree.engine.moon.render.pipeline.material.AbstractMaterialProperty;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperty;
import com.greentree.engine.moon.render.pipeline.material.ProxyMaterialProperty;

public class OpenGLMaterialPropertiesImpl extends MaterialPropertiesBase
		implements OpenGLMaterialProperties {
	
	@Override
	public Map<? extends String, ? extends Integer> ints() {
		final var result = new HashMap<>(super.ints());
		for(var p : properties.entrySet()) {
			if(p.getValue() instanceof TextureProperty v) {
				final var texture = v.texture;
				
				//				texture.bind();
				
			}
		}
		return result;
	}
	
	@Override
	public void put(String name, GLTexture texture) {
		final var p = new TextureProperty();
		properties.put(name, p);
		p.setTexture(texture);
	}
	
	@Override
	public OpenGLMaterialProperty get(String name) {
		return AdapterOpenGLMaterialProperty.create(super.get(name));
	}
	
	@Override
	protected MaterialProperty newTypeDefinitionProperty(String name) {
		return new TypeDefinitionProperty(name, super.newTypeDefinitionProperty(name));
	}
	
	private static final class TextureProperty
			implements OpenGLMaterialProperty, AbstractMaterialProperty {
		
		private GLTexture texture;
		
		@Override
		public void setTexture(GLTexture texture) {
			this.texture = texture;
		}
		
	}
	
	private final class TypeDefinitionProperty extends ProxyMaterialProperty
			implements OpenGLMaterialProperty, AbstractMaterialProperty {
		
		private final String name;
		
		public TypeDefinitionProperty(String name, MaterialProperty property) {
			super(property);
			this.name = name;
		}
		
		@Override
		public void setTexture(GLTexture texture) {
			final var p = new TextureProperty();
			properties.put(name, p);
			p.setTexture(texture);
		}
		
	}
	
}

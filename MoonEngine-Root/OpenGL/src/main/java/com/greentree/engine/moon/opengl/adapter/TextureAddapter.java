package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.opengl.GL13.*;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.render.pipeline.material.Property;
import com.greentree.engine.moon.render.pipeline.material.PropertyBindContext;
import com.greentree.engine.moon.render.pipeline.material.PropertyLocation;


public record TextureAddapter(GLTexture texture) implements Property {
	
	
	@Override
	public String toString() {
		return texture.toString();
	}
	
	@Override
	public void bind(PropertyLocation location, PropertyBindContext context) {
		var slot = context.nextEmptyTextureSlot();
		location.setInt(slot);
		glActiveTexture(GL_TEXTURE0 + slot);
		texture.bind();
		glActiveTexture(GL_TEXTURE0);
		
	}
	
}

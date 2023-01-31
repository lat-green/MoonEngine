package com.greentree.engine.moon.opengl.render.material;

import org.lwjgl.opengl.GL13;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.render.material.PropertyContext;
import com.greentree.engine.moon.render.material.TextureProperty;
import com.greentree.engine.moon.render.shader.UniformLocation;

public record GLTextureProperty(GLTexture texture) implements TextureProperty {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void set(UniformLocation location, PropertyContext context) {
		final var tex = context.nextEmptyTexture();
		GL13.glActiveTexture(tex);
		location.seti(tex);
		texture.bind();
		GL13.glActiveTexture(0);
	}
	
}

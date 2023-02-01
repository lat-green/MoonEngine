package com.greentree.engine.moon.opengl.render.material;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.render.material.MaterialProperty;
import com.greentree.engine.moon.render.shader.UniformLocation;

public record GLTextureProperty(GLTexture texture) implements MaterialProperty {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void set(UniformLocation location) {
		throw new UnsupportedOperationException();
		//		final var tex = context.nextEmptyTexture();
		//		GL13.glActiveTexture(tex);
		//		location.seti(tex);
		//		texture.bind();
		//		GL13.glActiveTexture(0);
	}
	
}

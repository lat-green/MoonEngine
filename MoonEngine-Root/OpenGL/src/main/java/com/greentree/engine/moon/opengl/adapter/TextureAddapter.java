package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.opengl.GL13.*;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.render.texture.Texture;


public record TextureAddapter(GLTexture texture) implements Texture {
	
	@Override
	public void bint(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
		texture.bind();
		glActiveTexture(GL_TEXTURE0);
	}
	
	@Override
	public String toString() {
		return texture.toString();
	}
	
}

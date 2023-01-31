package com.greentree.engine.moon.opengl.command;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.enums.gl.GLClientState;
import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2D;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.renderer.material.TextureProperty;
import com.greentree.common.renderer.opengl.material.GLTextureProperty;

public record DrawTexture(TextureProperty texture) implements OpenGLCommand {
	
	private static void renderTexture(GLTexture2D texture) {
		texture.bind();
		glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		glEnableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
		try(var stack = MemoryStack.create(GLType.FLOAT.size * 2 * 8).push()) {
			glVertexPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(-1f, -1f, -1f, 1f, 1f, 1f, 1f, -1f));
			glTexCoordPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(0, 0, 0, 1, 1, 1, 1, 0));
		}
		glDrawArrays(GLPrimitive.QUADS.glEnum, 0, 4);
		glDisableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
		glDisableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		GLTexture2DImpl.BINDER.unbind();
	}
	
	@Override
	public void run(OpenGLContext context) {
		final var tex = ((GLTextureProperty) texture).value();
		renderTexture((GLTexture2D) tex);
	}
	
}

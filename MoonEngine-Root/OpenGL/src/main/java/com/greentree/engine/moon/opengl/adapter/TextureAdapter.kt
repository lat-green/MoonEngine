package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.texture.gl.GLTexture
import com.greentree.engine.moon.render.material.PropertyLocation
import com.greentree.engine.moon.render.texture.Texture
import org.lwjgl.opengl.GL13.*

data class TextureAdapter(val texture: GLTexture) : Texture {

	override fun toString(): String {
		return texture.toString()
	}

	override fun bind(location: PropertyLocation) {
		location as OpenGLPropertyLocation
		val slot = location.ctx.nextEmptyTextureSlot()
		location.setInt(slot)
		glActiveTexture(GL_TEXTURE0 + slot)
		texture.bind()
		glActiveTexture(GL_TEXTURE0)
	}
}
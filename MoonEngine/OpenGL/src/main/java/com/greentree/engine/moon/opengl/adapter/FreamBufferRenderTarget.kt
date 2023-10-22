package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer
import com.greentree.commons.graphics.smart.target.FrameBuffer
import com.greentree.commons.graphics.smart.target.RenderCommandBuffer
import com.greentree.commons.graphics.smart.target.RenderTarget
import com.greentree.commons.graphics.smart.texture.Texture
import java.util.*

data class FreamBufferRenderTarget(val framebuffer: FreamBuffer, val context: RenderTarget) : FrameBuffer {

	override fun buffer(): RenderCommandBuffer {
		return FreamBufferCommandBuffer(framebuffer, context.buffer())
	}

	override fun getColorTexture(index: Int): Texture {
		val texture = framebuffer.getColorTexture(index)
		return OpenGLTexture(texture)
	}

	override fun getDepthTexture(): Texture {
		val texture = framebuffer.depthTexture
		return OpenGLTexture(texture)
	}

	init {
		Objects.requireNonNull(framebuffer)
		Objects.requireNonNull(context)
	}
}
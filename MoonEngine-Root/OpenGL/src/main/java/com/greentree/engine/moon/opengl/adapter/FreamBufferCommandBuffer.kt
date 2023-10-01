package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer
import com.greentree.commons.graphics.smart.target.RenderCommandBuffer
import org.lwjgl.opengl.GL11

class FreamBufferCommandBuffer(val fb: FreamBuffer, val origin: RenderCommandBuffer) : RenderCommandBuffer by origin {

	override fun execute() {
		fb.bind()
		GL11.glViewport(0, 0, fb.width, fb.height)
		origin.execute()
		fb.unbind()
	}
}
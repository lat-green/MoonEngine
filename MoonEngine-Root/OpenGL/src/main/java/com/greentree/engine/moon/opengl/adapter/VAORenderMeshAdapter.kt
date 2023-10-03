package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.vao.GLVertexArray
import com.greentree.commons.graphics.smart.VideoBuffer
import org.lwjgl.opengl.GL11

data class VAORenderMeshAdapter(val vao: GLVertexArray) : RenderMesh {

	override fun add(location: Int, vbo: VideoBuffer, size: Int, stride: Int, offset: Int, divisor: Int) {
		TODO("Not yet implemented")
	}

	override fun has(location: Int): Boolean {
		TODO("Not yet implemented")
	}

	override fun remove(location: Int) {
		TODO("Not yet implemented")
	}

	override fun vbo(location: Int): VideoBuffer {
		TODO("Not yet implemented")
	}

	override fun render() {
		vao.bind()
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.size())
		vao.unbind()
	}
}
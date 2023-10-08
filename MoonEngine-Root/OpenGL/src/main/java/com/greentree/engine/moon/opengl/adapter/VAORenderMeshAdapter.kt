package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.vao.GLVertexArray
import com.greentree.commons.graphics.smart.mesh.VideoBuffer
import com.greentree.engine.moon.opengl.vbo.OpenGLVideoBuffer
import org.lwjgl.opengl.GL45.*

data class VAORenderMeshAdapter(val vao: GLVertexArray) : RenderMesh {

	private val vbos = mutableMapOf<Int, VideoBuffer>()

	override fun add(location: Int, vbo: VideoBuffer, size: Int, stride: Int, pointer: Int, divisor: Int) {
		vbo as OpenGLVideoBuffer
		if(vbo.target != GL_ARRAY_BUFFER)
			throw UnsupportedOperationException("target = ${vbo.target}")
		glBindVertexArray(vao.__glID())
		glBindBuffer(vbo.target, vbo.glID)
		glEnableVertexAttribArray(location)
		glVertexAttribPointer(location, size, GL_FLOAT, false, stride, pointer.toLong())
		glVertexAttribDivisor(location, divisor)
		glBindBuffer(vbo.target, 0)
		glBindVertexArray(0)
		vbos[location] = vbo
	}

	override fun has(location: Int): Boolean {
		return glGetVertexArrayIndexedi(vao.__glID(), location, GL_VERTEX_ATTRIB_ARRAY_ENABLED) != 0
	}

	override fun remove(location: Int) {
		glDisableVertexArrayAttrib(vao.__glID(), location)
	}

	override fun vbo(location: Int) = vbos[location]!!

	override fun render(count: Int) {
		vao.bind()
		glDrawArraysInstanced(GL_TRIANGLES, 0, vao.size(), count)
		vao.unbind()
	}
}
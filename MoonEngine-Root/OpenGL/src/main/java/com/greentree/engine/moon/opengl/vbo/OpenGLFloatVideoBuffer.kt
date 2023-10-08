package com.greentree.engine.moon.opengl.vbo

import com.greentree.common.graphics.sgl.enums.gl.GLType
import com.greentree.commons.graphics.smart.mesh.FloatVideoBuffer
import org.lwjgl.opengl.GL15.*
import java.nio.ByteBuffer
import java.nio.FloatBuffer

class OpenGLFloatVideoBuffer(override val target: Int) : OpenGLVideoBuffer, FloatVideoBuffer {

	override val glID = glGenBuffers()

	override fun getData(data: ByteBuffer) = super<OpenGLVideoBuffer>.getData(data)

	override fun setData(data: ByteBuffer) = super<OpenGLVideoBuffer>.setData(data)

	init {
		glBindBuffer(target, glID)
		glBufferData(target, 0, GL_STATIC_DRAW)
		glBindBuffer(target, 0)
	}

	override val elementSize: Int
		get() = GLType.FLOAT.size

	override fun getData(buffer: FloatBuffer) {
		glBindBuffer(target, glID)
		glGetBufferSubData(target, 0, buffer)
		glBindBuffer(target, 0)
	}

	override fun setData(buffer: FloatBuffer) {
		glBindBuffer(target, glID)
		glBufferData(target, buffer, GL_STATIC_DRAW)
		glBindBuffer(target, 0)
	}
}
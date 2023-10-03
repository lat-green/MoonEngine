package com.greentree.engine.moon.opengl.adapter

import com.greentree.commons.graphics.smart.VideoBuffer
import org.lwjgl.opengl.GL15.*
import java.nio.ByteBuffer

class OpenGLVideoBuffer(private val target: Int, override val elementSize: Int, private val buffer: Int) : VideoBuffer {

	override val elementCount: Int
		get() {
			glBindBuffer(target, buffer)
			val result = glGetBufferParameteri(target, GL_BUFFER_SIZE) / elementSize
			glBindBuffer(target, 0)
			return result
		}

	override fun getData(data: ByteBuffer) {
		glBindBuffer(target, buffer)
		glBufferData(target, data, GL_STATIC_DRAW)
		glBindBuffer(target, 0)
	}

	override fun setData(data: ByteBuffer) {
		glBindBuffer(target, buffer)
		glGetBufferSubData(target, 0, data)
		glBindBuffer(target, 0)
	}
}

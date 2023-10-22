package com.greentree.engine.moon.opengl.vbo

import com.greentree.commons.graphics.smart.mesh.FloatVideoBuffer
import com.greentree.commons.graphics.smart.mesh.VideoBuffer
import org.lwjgl.opengl.GL15.*
import java.nio.ByteBuffer

interface OpenGLVideoBuffer : VideoBuffer {

	val target: Int
	val glID: Int

	class Builder : VideoBuffer.Builder {

		override fun buildFloat(): FloatVideoBuffer {
			return OpenGLFloatVideoBuffer(GL_ARRAY_BUFFER)
		}
	}

	override val elementCount: Int
		get() {
			glBindBuffer(target, glID)
			val result = glGetBufferParameteri(target, GL_BUFFER_SIZE) / elementSize
			glBindBuffer(target, 0)
			return result
		}

	override fun getData(data: ByteBuffer) {
		glBindBuffer(target, glID)
		glBufferSubData(target, 0, data)
		glBindBuffer(target, 0)
	}

	override fun setData(data: ByteBuffer) {
		glBindBuffer(target, glID)
		glBufferData(target, data, GL_STATIC_DRAW)
		glBindBuffer(target, 0)
	}
}

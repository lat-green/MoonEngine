package com.greentree.engine.moon.opengl.adapter

import com.greentree.commons.graphics.smart.shader.BindingPoint
import org.lwjgl.opengl.GL45.*

private var count = 0

class OpenGLBindingPoint(size: Long) : BindingPoint {

	val index = count++
	private val buffer = run {
		val buffer = glGenBuffers()
		glBindBuffer(GL_UNIFORM_BUFFER, buffer)
		glBufferData(GL_UNIFORM_BUFFER, 0, GL_STATIC_DRAW)
		glBindBuffer(GL_UNIFORM_BUFFER, 0)
		glBindBufferRange(GL_UNIFORM_BUFFER, 0, buffer, 0, size)
		OpenGLVideoBuffer(GL_UNIFORM_BUFFER, 1, buffer)
	}

	override fun buffer() = buffer
}

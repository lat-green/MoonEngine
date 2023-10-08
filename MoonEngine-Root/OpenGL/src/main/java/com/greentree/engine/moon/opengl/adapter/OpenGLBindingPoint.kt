package com.greentree.engine.moon.opengl.adapter

import com.greentree.commons.graphics.smart.shader.BindingPoint
import com.greentree.engine.moon.opengl.vbo.OpenGLByteVideoBuffer
import org.lwjgl.opengl.GL45.*

private var count = 0

class OpenGLBindingPoint : BindingPoint {

	val index = count++
	private val buffer = OpenGLByteVideoBuffer(GL_UNIFORM_BUFFER)

	override fun buffer() = buffer
}

package com.greentree.engine.moon.opengl.adapter

import com.greentree.commons.graphics.smart.StackBuffer
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

class LWJGLStackBuffer(private val stack: MemoryStack) : StackBuffer {

	override fun close() = stack.close()

	override fun malloc(alignment: Int, size: Int): ByteBuffer = stack.malloc(alignment, size)

	override fun mallocByte(size: Int): ByteBuffer = stack.malloc(size)

	override fun mallocFloat(size: Int): FloatBuffer = stack.mallocFloat(size)

	override fun mallocInt(size: Int): IntBuffer = stack.mallocInt(size)

	override fun floats(vararg values: Float): FloatBuffer = stack.floats(*values)

	override fun ints(vararg values: Int): IntBuffer = stack.ints(*values)
}

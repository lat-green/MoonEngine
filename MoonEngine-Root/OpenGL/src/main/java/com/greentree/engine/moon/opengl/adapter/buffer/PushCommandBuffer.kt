package com.greentree.engine.moon.opengl.adapter.buffer

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.commons.graphics.smart.target.RenderCommandBuffer
import com.greentree.commons.image.Color
import com.greentree.engine.moon.opengl.adapter.GLRenderLibrary
import com.greentree.engine.moon.opengl.adapter.OpenGLMaterial
import com.greentree.engine.moon.opengl.adapter.RenderMesh
import com.greentree.engine.moon.opengl.adapter.buffer.command.ClearRenderTargetColor
import com.greentree.engine.moon.opengl.adapter.buffer.command.ClearRenderTargetDepth
import com.greentree.engine.moon.opengl.adapter.buffer.command.CullFace
import com.greentree.engine.moon.opengl.adapter.buffer.command.DepthTest
import com.greentree.engine.moon.opengl.adapter.buffer.command.DrawMesh
import com.greentree.engine.moon.opengl.adapter.buffer.command.TargetCommand
import java.util.*

class PushCommandBuffer @JvmOverloads constructor(
	private val library: GLRenderLibrary,
	initialCapacity: Int = INITIAL_CAPACITY
) : RenderCommandBuffer {

	private val commands: MutableList<TargetCommand>
	private var depthTest = false
	private var cullFace = false
	private var material: OpenGLMaterial? = null
	private var mesh: RenderMesh? = null

	init {
		commands = ArrayList(initialCapacity)
	}

	override fun bindMaterial(material: Material) {
		this.material = material as OpenGLMaterial
	}

	override fun bindMesh(mesh: Mesh) {
		this.mesh = mesh as RenderMesh
	}

	override fun clear() {
		commands.clear()
		cullFace = false
		depthTest = false
	}

	override fun clearColor(color: Color) {
		push(ClearRenderTargetColor(color))
	}

	override fun clearDepth(depth: Float) {
		push(ClearRenderTargetDepth(depth))
	}

	override fun disableCullFace() {
		cullFace = false
	}

	override fun disableDepthTest() {
		depthTest = false
	}

	override fun draw() {
		var command: TargetCommand = DrawMesh(mesh!!, material!!)
		if(cullFace) command = CullFace(command)
		if(depthTest) command = DepthTest(command)
		push(command)
	}

	override fun enableCullFace() {
		cullFace = true
	}

	override fun enableDepthTest() {
		depthTest = true
	}

	override fun execute() {
		for(c in commands) c.run()
	}

	fun push(command: TargetCommand) {
		var command = command
		Objects.requireNonNull(command)
		while(commands.size > 1) {
			val c = commands[commands.size - 1]
			val m = c.merge(command) ?: break
			command = m
			commands.removeAt(commands.size - 1)
		}
		commands.add(command)
	}

	companion object {

		private const val INITIAL_CAPACITY = 10
	}
}
package com.greentree.engine.moon.opengl.adapter.buffer

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.commons.graphics.smart.target.RenderCommandBuffer
import com.greentree.commons.image.Color
import com.greentree.engine.moon.opengl.adapter.GLRenderContext
import com.greentree.engine.moon.opengl.adapter.OpenGLMaterial
import com.greentree.engine.moon.opengl.adapter.RenderMesh
import com.greentree.engine.moon.opengl.adapter.buffer.command.ClearRenderTargetColor
import com.greentree.engine.moon.opengl.adapter.buffer.command.ClearRenderTargetDepth
import com.greentree.engine.moon.opengl.adapter.buffer.command.CullFace
import com.greentree.engine.moon.opengl.adapter.buffer.command.DepthTest
import com.greentree.engine.moon.opengl.adapter.buffer.command.DrawMesh
import com.greentree.engine.moon.opengl.adapter.buffer.command.TargetCommand

class PushCommandBuffer @JvmOverloads constructor(
	private val library: GLRenderContext,
	initialCapacity: Int = INITIAL_CAPACITY,
) : RenderCommandBuffer {

	private val commands: MutableList<TargetCommand>
	private var depthTest = false
	private var cullFace = false
	private var material: OpenGLMaterial? = null
	private var mesh: RenderMesh? = null
	private var optimazed = true

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

	override fun draw(count: Int) {
		var command: TargetCommand = DrawMesh(mesh!!, material!!, count)
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
		if(!optimazed) {
			optimazed = true
//			val cs = ArrayList<TargetCommand>(commands.size)
//			var c: Int
//			do {
//				c = 0
//				var i = 0
//				while(i < commands.size - 1) {
//					val a = commands[i]
//					val b = commands[i + 1]
//					val m = a.merge(b)
//					if(m == null) {
//						cs.add(a)
//					} else {
//						cs.add(m)
//						i++
//						c++
//					}
//					i++
//				}
//				commands.clear()
//				commands.addAll(cs)
//				cs.clear()
//			} while(c > 0)
//
		}
		for(c in commands) c.run()
	}

	private fun push(command: TargetCommand) {
//		optimazed = false
		var command = command
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
package com.greentree.engine.moon.opengl.adapter.buffer.command

import com.greentree.engine.moon.opengl.adapter.OpenGLMaterial
import com.greentree.engine.moon.opengl.adapter.RenderMesh

data class DrawMesh(
	private val mesh: RenderMesh,
	private val material: OpenGLMaterial,
	private val count: Int,
) : TargetCommand {

	override fun run() {
		material.bind()
		mesh.render(count)
		material.unbind()
	}

	override fun toString(): String {
		return "DrawMesh [$material, $mesh, $count]"
	}

	companion object {

		private fun <T> merge(a: Collection<T>, b: Collection<T>): Collection<T> {
			val result = ArrayList<T>(a.size + b.size)
			result.addAll(a)
			result.addAll(b)
			result.trimToSize()
			return result
		}
	}
}
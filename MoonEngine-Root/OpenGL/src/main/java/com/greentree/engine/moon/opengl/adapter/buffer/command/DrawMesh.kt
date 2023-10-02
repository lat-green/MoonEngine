package com.greentree.engine.moon.opengl.adapter.buffer.command

import com.greentree.engine.moon.opengl.adapter.OpenGLMaterial
import com.greentree.engine.moon.opengl.adapter.RenderMesh

data class DrawMesh(private val meshs: Collection<RenderMesh>, private val material: OpenGLMaterial) : TargetCommand {

	constructor(mesh: RenderMesh, material: OpenGLMaterial) : this(listOf(mesh), material)

	override fun run() {
		material.bind()
		for(mesh in meshs)
			mesh.render()
		material.unbind()
	}

	override fun merge(command: TargetCommand): TargetCommand? {
		if(command === this) return this
		if(command is DrawMesh && command.material == material)
			return DrawMesh(merge(meshs, command.meshs), material)
		return null
	}

	override fun toString(): String {
		return "DrawMesh [$material, $meshs]"
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
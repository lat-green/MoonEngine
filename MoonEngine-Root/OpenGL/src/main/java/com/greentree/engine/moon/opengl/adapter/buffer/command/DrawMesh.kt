package com.greentree.engine.moon.opengl.adapter.buffer.command

import com.greentree.engine.moon.opengl.adapter.OpenGLMaterial
import com.greentree.engine.moon.opengl.adapter.RenderMesh
import java.util.*
import java.util.List

data class DrawMesh(val mesh: RenderMesh, val properties: Iterable<OpenGLMaterial>) : TargetCommand {

	constructor(mesh: RenderMesh, properties: OpenGLMaterial) : this(mesh, List.of<OpenGLMaterial>(properties))

	override fun run() {
		mesh.bind()
		val iter = properties.iterator()
		while(iter.hasNext()) {
			val p = iter.next()
			p.bind()
			mesh.render()
			p.unbind()
		}
		mesh.unbind()
	}

	override fun merge(command: TargetCommand): TargetCommand? {
		if(command === this) return this
		if(command is DrawMesh && command.mesh == mesh)
			return DrawMesh(mesh, merge(properties, command.properties))
		return null
	}

	override fun toString(): String {
		return "DrawMesh [$properties, $mesh]"
	}

	init {
		Objects.requireNonNull(mesh)
		Objects.requireNonNull(properties)
	}

	companion object {

		private fun <T> merge(a: Iterable<T>, b: Iterable<T>): Collection<T> {
			val result = ArrayList<T>()
			for(e in a) result.add(e)
			for(e in b) result.add(e)
			result.trimToSize()
			return result
		}
	}
}
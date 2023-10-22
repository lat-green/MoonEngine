package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.enums.gl.GLType
import com.greentree.common.graphics.sgl.shader.GLUniformLocation
import com.greentree.commons.graphics.smart.shader.material.AbstractMaterial
import com.greentree.commons.graphics.smart.texture.Texture
import org.lwjgl.opengl.GL13
import org.lwjgl.system.MemoryStack

open class AbstractMaterialImpl : AbstractMaterial {

	val properties = mutableMapOf<String, (GLUniformLocation, PropertyBindContext) -> Unit>()

	override fun put(name: String, texture: Texture) {
		texture as OpenGLTexture
		properties[name] = { loc, ctx ->
			val slot = ctx.nextEmptyTextureSlot()
			loc.seti(slot)
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot)
			texture.texture.bind()
			GL13.glActiveTexture(GL13.GL_TEXTURE0)
		}
	}

	override fun put(name: String, x1: Float) {
		properties[name] = { loc, ctx -> loc.setf(x1) }
	}

	override fun put(name: String, x1: Float, x2: Float) {
		properties[name] = { loc, ctx -> loc.setf(x1, x2) }
	}

	override fun put(name: String, x1: Float, x2: Float, x3: Float) {
		properties[name] = { loc, ctx -> loc.setf(x1, x2, x3) }
	}

	override fun put(name: String, x1: Float, x2: Float, x3: Float, x4: Float) {
		properties[name] = { loc, ctx -> loc.setf(x1, x2, x3, x4) }
	}

	override fun put(
		name: String,
		m00: Float,
		m01: Float,
		m02: Float,
		m03: Float,
		m10: Float,
		m11: Float,
		m12: Float,
		m13: Float,
		m20: Float,
		m21: Float,
		m22: Float,
		m23: Float,
		m30: Float,
		m31: Float,
		m32: Float,
		m33: Float,
	) {
		properties[name] = { loc, ctx ->
			MemoryStack.create(GLType.FLOAT.size * 16).push().use { stack ->
				loc.set4fv(
					stack.floats(
						m00, m01, m02, m03,
						m10, m11, m12, m13,
						m20, m21, m22, m23,
						m30, m31, m32, m33,
					)
				)
			}
		}
	}

	override fun put(name: String, x1: Int) {
		properties[name] = { loc, ctx -> loc.seti(x1) }
	}

	override fun put(name: String, x1: Int, x2: Int) {
		TODO("Not yet implemented")
	}

	override fun put(name: String, x1: Int, x2: Int, x3: Int) {
		TODO("Not yet implemented")
	}

	override fun put(name: String, x1: Int, x2: Int, x3: Int, x4: Int) {
		TODO()
	}
}

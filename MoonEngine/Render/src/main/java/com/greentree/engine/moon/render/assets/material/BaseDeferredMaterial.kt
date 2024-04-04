package com.greentree.engine.moon.render.assets.material

import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.shader.material.AbstractMaterial
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.commons.graphics.smart.texture.Texture

class BaseDeferredMaterial : DeferredMaterial {

	private val properties = mutableListOf<AbstractMaterial.() -> Unit>()

	override fun put(name: String, texture: Texture) {
		properties.add { put(name, texture) }
	}

	override fun put(name: String, x1: Float) {
		properties.add { put(name, x1) }
	}

	override fun put(name: String, x1: Float, x2: Float) {
		properties.add { put(name, x1, x2) }
	}

	override fun put(name: String, x1: Float, x2: Float, x3: Float) {
		properties.add { put(name, x1, x2, x3) }
	}

	override fun put(name: String, x1: Float, x2: Float, x3: Float, x4: Float) {
		properties.add { put(name, x1, x2, x3, x4) }
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
		properties.add {
			put(
				name,
				m00, m01, m02, m03,
				m10, m11, m12, m13,
				m20, m21, m22, m23,
				m30, m31, m32, m33,
			)
		}
	}

	override fun put(name: String, x1: Int) {
		properties.add { put(name, x1) }
	}

	override fun put(name: String, x1: Int, x2: Int) {
		properties.add { put(name, x1, x2) }
	}

	override fun put(name: String, x1: Int, x2: Int, x3: Int) {
		properties.add { put(name, x1, x2, x3) }
	}

	override fun put(name: String, x1: Int, x2: Int, x3: Int, x4: Int) {
		properties.add { put(name, x1, x2, x3, x4) }
	}

	override fun copy(): DeferredMaterial {
		val copy = BaseDeferredMaterial()
		copy.properties.addAll(properties)
		return copy
	}

	override fun build(shader: Shader): Material {
		val m = shader.createMaterial()
		for(property in properties)
			property(m)
		return m
	}
}
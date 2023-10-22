package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.shader.GLShaderProgram
import com.greentree.commons.graphics.smart.shader.material.AbstractMaterial
import com.greentree.commons.graphics.smart.shader.material.Material

interface OpenGLMaterial : Material {

	val shader: GLShaderProgram

	fun copyTo(material: AbstractMaterial)

	fun bind(
		ctx: PropertyBindContext = object : PropertyBindContext {
			var slot = 0
			override fun nextEmptyTextureSlot() = ++slot
		},
	)

	fun unbind()
}
package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.shader.GLShaderProgram
import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.shader.material.AbstractMaterial

data class OpenGLShader(val shader: GLShaderProgram) : Shader {

	override fun newMaterial() = OpenGLMaterialImpl(shader)

	class OpenGLMaterialImpl(override val shader: GLShaderProgram) : OpenGLMaterial, AbstractMaterialImpl() {

		override fun bind(ctx: PropertyBindContext) {
			shader.bind()
			for((k, v) in properties)
				v(shader.getUL(k), ctx)
		}

		override fun unbind() {
			shader.unbind()
		}

		override fun copy(): OpenGLMaterialImpl {
			val copy = OpenGLMaterialImpl(shader)
			copyTo(copy)
			return copy
		}

		override fun copyTo(material: AbstractMaterial) {
			material as AbstractMaterialImpl
			material.properties.putAll(properties)
		}

		override fun newMaterial() = Child(this)

		class Child(
			private val parent: OpenGLMaterial,
		) : OpenGLMaterial, AbstractMaterialImpl() {

			override val shader
				get() = parent.shader

			override fun newMaterial() = Child(this)

			override fun bind(ctx: PropertyBindContext) {
				parent.bind(ctx)
				for((k, v) in properties)
					v(shader.getUL(k), ctx)
			}

			override fun unbind() {
				parent.unbind()
			}

			override fun copyTo(material: AbstractMaterial) {
				parent.copyTo(material)
				material as AbstractMaterialImpl
				material.properties.putAll(properties)
			}

			override fun copy(): OpenGLMaterialImpl {
				val copy = OpenGLMaterialImpl(shader)
				parent.copyTo(copy)
				copyTo(copy)
				return copy
			}
		}
	}
}
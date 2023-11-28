package com.greentree.engine.moon.opengl.assets.shader

import com.greentree.common.graphics.sgl.shader.GLShaderProgram
import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.opengl.adapter.OpenGLShader

object ShaderAssetSerializator : AssetSerializator<Shader> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<OpenGLShader> {
		val program = manager.load<GLShaderProgram>(key)
		return program.map(GLShaderProgramToOpenGLShader)
	}

	private object GLShaderProgramToOpenGLShader : Value1Function<GLShaderProgram, OpenGLShader> {

		override fun apply(program: GLShaderProgram) = OpenGLShader(program)
	}
}
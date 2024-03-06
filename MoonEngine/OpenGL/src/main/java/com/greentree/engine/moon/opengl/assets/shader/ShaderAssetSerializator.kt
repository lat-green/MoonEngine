package com.greentree.engine.moon.opengl.assets.shader

import com.greentree.common.graphics.sgl.shader.GLShaderProgram
import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.opengl.adapter.OpenGLShader

data object ShaderAssetSerializator : AssetSerializator<Shader> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): OpenGLShader {
		val program = manager.load<GLShaderProgram>(key)
		return OpenGLShader(program)
	}
}
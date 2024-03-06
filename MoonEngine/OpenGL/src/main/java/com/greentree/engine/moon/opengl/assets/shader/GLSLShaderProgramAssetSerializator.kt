package com.greentree.engine.moon.opengl.assets.shader

import com.greentree.common.graphics.sgl.shader.GLSLShader
import com.greentree.common.graphics.sgl.shader.GLShaderProgram
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.opengl.GLEnums
import com.greentree.engine.moon.render.shader.ShaderProgramData

data object GLSLShaderProgramAssetSerializator : AssetSerializator<GLShaderProgram> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): GLShaderProgram {
		val data = manager.load(ShaderProgramData::class.java, key)
		val shaders = ArrayList<GLSLShader>(5)
		for(s in data) shaders.add(GLSLShader(s.text(), GLEnums.get(s.type())))
		shaders.trimToSize()
		return GLShaderProgram(shaders)
	}
}

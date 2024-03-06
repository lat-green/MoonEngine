package com.greentree.engine.moon.opengl.assets.shader

import com.greentree.common.graphics.sgl.shader.GLSLShader
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.opengl.GLEnums
import com.greentree.engine.moon.render.shader.ShaderData

data object GLSLShaderAssetSerializator : AssetSerializator<GLSLShader> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): GLSLShader {
		val shader = manager.load(ShaderData::class.java, key)
		return GLSLShader(shader.text(), GLEnums.get(shader.type()))
	}
}

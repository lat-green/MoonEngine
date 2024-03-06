package com.greentree.engine.moon.render.assets.shader

import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.render.shader.ShaderDataImpl
import com.greentree.engine.moon.render.shader.ShaderLanguage
import com.greentree.engine.moon.render.shader.ShaderType

data object ShaderDataAssetSerializator : AssetSerializator<ShaderDataImpl> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): ShaderDataImpl {
		if(ckey is ShaderAssetKey) {
			val text = manager.load<String>(ckey.text)
			val type: ShaderType = ckey.shaderType
			val language: ShaderLanguage = ckey.language
			return ShaderDataImpl(text, type, language)
		}
		throw NotSupportedKeyType
	}
}

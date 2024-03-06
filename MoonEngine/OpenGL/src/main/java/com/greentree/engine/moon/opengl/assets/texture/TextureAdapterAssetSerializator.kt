package com.greentree.engine.moon.opengl.assets.texture

import com.greentree.common.graphics.sgl.texture.gl.GLTexture
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.opengl.adapter.OpenGLTexture

data object TextureAdapterAssetSerializator : AssetSerializator<OpenGLTexture> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): OpenGLTexture {
		val texture = manager.load<GLTexture>(ckey)
		return OpenGLTexture(texture)
	}
}
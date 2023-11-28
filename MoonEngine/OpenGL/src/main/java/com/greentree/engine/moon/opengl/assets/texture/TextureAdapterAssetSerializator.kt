package com.greentree.engine.moon.opengl.assets.texture

import com.greentree.common.graphics.sgl.texture.gl.GLTexture
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.opengl.adapter.OpenGLTexture

class TextureAdapterAssetSerializator : AssetSerializator<OpenGLTexture> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): Asset<OpenGLTexture> {
		val texture = manager.load<GLTexture>(ckey)
		return texture.map(GLTextureToTextureAdapter)
	}

	private object GLTextureToTextureAdapter : Value1Function<GLTexture, OpenGLTexture> {

		override fun apply(texture: GLTexture): OpenGLTexture {
			return OpenGLTexture(texture)
		}
	}
}
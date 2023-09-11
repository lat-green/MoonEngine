package com.greentree.engine.moon.opengl.assets.texture

import com.greentree.common.graphics.sgl.texture.gl.GLTexture
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.opengl.adapter.TextureAddapter

class TextureAdapterAssetSerializator : AssetSerializator<TextureAddapter> {

	override fun load(manager: AssetManager, ckey: AssetKey): Asset<TextureAddapter> {
		val texture = manager.load<GLTexture>(ckey)
		return texture.map(GLTextureToTextureAdapter)
	}

	private object GLTextureToTextureAdapter : Value1Function<GLTexture, TextureAddapter> {

		override fun apply(texture: GLTexture): TextureAddapter {
			return TextureAddapter(texture)
		}
	}
}
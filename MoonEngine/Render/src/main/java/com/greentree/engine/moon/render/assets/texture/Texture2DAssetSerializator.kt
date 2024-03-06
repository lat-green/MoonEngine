package com.greentree.engine.moon.render.assets.texture

import com.greentree.commons.image.image.ImageData
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.render.texture.Texture2DData
import com.greentree.engine.moon.render.texture.Texture2DType

data object Texture2DAssetSerializator : AssetSerializator<Texture2DData> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Texture2DData {
		if(key is Texture2DAssetKey) {
			val image = manager.load<ImageData>(key.image)
			val type = key.textureType
			return Texture2DData(image, type)
		}
		val image = manager.load<ImageData>(key)
		return Texture2DData(image, Texture2DType())
	}
}

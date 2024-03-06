package com.greentree.engine.moon.render.assets.texture.cube

import com.greentree.engine.moon.assets.exception.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.render.texture.CubeImageData
import com.greentree.engine.moon.render.texture.CubeTextureData

data object CubeTextureAssetSerializator : AssetSerializator<CubeTextureData> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): CubeTextureData {
		if(ckey is CubeTextureAssetKey) {
			val image = manager.load<CubeImageData>(ckey.image)
			val type = ckey.textureType
			return CubeTextureData(image, type)
		}
		throw NotSupportedKeyType
	}
}
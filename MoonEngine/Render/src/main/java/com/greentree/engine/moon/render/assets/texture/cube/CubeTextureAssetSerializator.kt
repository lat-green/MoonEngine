package com.greentree.engine.moon.render.assets.texture.cube

import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.render.texture.CubeImageData
import com.greentree.engine.moon.render.texture.CubeTextureData
import com.greentree.engine.moon.render.texture.Texture3DType

class CubeTextureAssetSerializator : AssetSerializator<CubeTextureData> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): AssetProvider<CubeTextureData> {
		if(ckey is CubeTextureAssetKey) {
			val image = manager.load<CubeImageData>(ckey.image)
			val type = ckey.type().type
			return image.map(CubeImageToTexture(type))
		}
		throw NotSupportedKeyType
	}

	private class CubeImageToTexture(val type: Texture3DType) : Value1Function<CubeImageData, CubeTextureData> {

		override fun apply(image: CubeImageData): CubeTextureData {
			return CubeTextureData(image, type)
		}

		override fun toString() = "CubeImageToTexture"
	}
}
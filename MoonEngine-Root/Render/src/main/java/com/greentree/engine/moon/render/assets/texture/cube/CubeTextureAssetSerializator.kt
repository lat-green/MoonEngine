package com.greentree.engine.moon.render.assets.texture.cube

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.render.texture.CubeImageData
import com.greentree.engine.moon.render.texture.CubeTextureData
import com.greentree.engine.moon.render.texture.Texture3DType

class CubeTextureAssetSerializator : AssetSerializator<CubeTextureData> {

	override fun load(manager: AssetManager, ckey: AssetKey): Asset<CubeTextureData>? {
		if(ckey is CubeTextureAssetKey) {
			val image = manager.load<CubeImageData>(ckey.image)
			val type = ckey.type().type
			return image.map(CubeImageToTexture(type))
		}
		return null
	}

	private class CubeImageToTexture(val type: Texture3DType) : Value1Function<CubeImageData, CubeTextureData> {

		override fun apply(image: CubeImageData): CubeTextureData {
			return CubeTextureData(image, type)
		}

		override fun toString() = "CubeImageToTexture"
	}
}
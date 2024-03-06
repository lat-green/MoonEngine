package com.greentree.engine.moon.render.assets.image.cube

import com.greentree.commons.image.image.ImageData
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.render.texture.CubeImageData

data object CubeImageAssetSerializator : AssetSerializator<CubeImageData> {

	override fun load(context: AssetLoader.Context, ckey: AssetKey): CubeImageData {
		if(ckey is CubeImageAssetKey) {
			val posx = context.load<ImageData>(ckey.posx)
			val negx = context.load<ImageData>(ckey.negx)
			val posy = context.load<ImageData>(ckey.posy)
			val negy = context.load<ImageData>(ckey.negy)
			val posz = context.load<ImageData>(ckey.posz)
			val negz = context.load<ImageData>(ckey.negz)
			return CubeImageData(posx, negx, posy, negy, posz, negz)
		}
		throw NotSupportedKeyType
	}
}
package com.greentree.engine.moon.render.assets.image.cube

import com.greentree.commons.image.image.ImageData
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value6Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.render.texture.CubeImageData

class CubeImageAssetSerializator : AssetSerializator<CubeImageData> {

	override fun load(context: AssetManager, ckey: AssetKey): Asset<CubeImageData>? {
		if(ckey is CubeImageAssetKey) {
			val posx = context.load<ImageData>(ckey.posx)
			val negx = context.load<ImageData>(ckey.negx)
			val posy = context.load<ImageData>(ckey.posy)
			val negy = context.load<ImageData>(ckey.negy)
			val posz = context.load<ImageData>(ckey.posz)
			val negz = context.load<ImageData>(ckey.negz)
			return map(
				posx, negx,
				posy, negy,
				posz, negz,
				SideImageDataToCubeImageData
			)
		}
		return null
	}

	private object SideImageDataToCubeImageData :
		Value6Function<ImageData, ImageData, ImageData, ImageData, ImageData, ImageData, CubeImageData> {

		override fun apply(
			posx: ImageData, negx: ImageData,
			posy: ImageData, negy: ImageData,
			posz: ImageData, negz: ImageData,
		) = CubeImageData(posx, negx, posy, negy, posz, negz)

		override fun toString() = "SideImageDataToCubeImageData"
	}
}
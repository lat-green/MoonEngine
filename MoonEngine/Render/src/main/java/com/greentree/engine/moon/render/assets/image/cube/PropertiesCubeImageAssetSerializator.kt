package com.greentree.engine.moon.render.assets.image.cube

import com.greentree.commons.image.image.ImageData
import com.greentree.commons.util.iterator.IteratorUtil
import com.greentree.engine.moon.assets.exception.SourceNotValid
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey
import com.greentree.engine.moon.render.texture.CubeImageData

data object PropertiesCubeImageAssetSerializator : AssetSerializator<CubeImageData> {

	override fun load(context: AssetLoader.Context, key: AssetKey): CubeImageData {
		val posx_res = ResourceAssetKey(PropertyAssetKey(key, "posx"))
		val negx_res = ResourceAssetKey(PropertyAssetKey(key, "negx"))
		val posy_res = ResourceAssetKey(PropertyAssetKey(key, "posy"))
		val negy_res = ResourceAssetKey(PropertyAssetKey(key, "negy"))
		val posz_res = ResourceAssetKey(PropertyAssetKey(key, "posz"))
		val negz_res = ResourceAssetKey(PropertyAssetKey(key, "negz"))
		val posx = context.load<ImageData>(posx_res)
		val negx = context.load<ImageData>(negx_res)
		val posy = context.load<ImageData>(posy_res)
		val negy = context.load<ImageData>(negy_res)
		val posz = context.load<ImageData>(posz_res)
		val negz = context.load<ImageData>(negz_res)
		val iter = IteratorUtil.iterable(posx, negx, posy, negy, posz, negz)
		for(a in iter)
			for(b in iter)
				if(a.width != b.width || a.height != b.height)
					throw SourceNotValid
		return CubeImageData(posx, negx, posy, negy, posz, negz)
	}
}

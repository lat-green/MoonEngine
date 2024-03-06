package com.greentree.engine.moon.render.assets.image

import com.greentree.commons.image.Color
import com.greentree.commons.image.image.ColorImageData
import com.greentree.commons.image.image.ImageData
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

data object ColorImageAssetSerializator : AssetSerializator<ImageData> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): ImageData {
		val color = manager.load<Color>(key)
		return ColorImageData(color)
	}
}
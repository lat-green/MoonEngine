package com.greentree.engine.moon.render.assets.image

import com.greentree.commons.image.Color
import com.greentree.commons.image.image.ColorImageData
import com.greentree.commons.image.image.ImageData
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load

class ColorImageAssetSerializator : AssetSerializator<ImageData> {

	override fun load(manager: AssetManager, key: AssetKey): Asset<ImageData> {
		val color = manager.load<Color>(key)
		return color.map(ColorToImage)
	}

	private object ColorToImage : Value1Function<Color, ImageData> {

		override fun apply(color: Color): ImageData {
			return ColorImageData(color)
		}

		override fun toString(): String {
			return "ColorToImage"
		}
	}
}
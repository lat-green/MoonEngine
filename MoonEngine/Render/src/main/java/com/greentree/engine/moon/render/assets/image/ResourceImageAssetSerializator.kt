package com.greentree.engine.moon.render.assets.image

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.image.image.ImageData
import com.greentree.commons.image.loader.ImageIOImageLoader
import com.greentree.engine.moon.assets.serializator.SimpleAssetSerializator

class ResourceImageAssetSerializator : SimpleAssetSerializator<Resource, ImageData> {

	override fun apply(res: Resource): ImageData {
		res.open().use {
			return ImageIOImageLoader.IMAGE_DATA_LOADER.loadImage(it)
		}
	}
}

package com.greentree.engine.moon.render.assets.image

import com.greentree.commons.image.PixelFormat
import com.greentree.commons.image.image.ByteArrayImageData
import com.greentree.commons.image.image.ImageData
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader

class ImageDataDefaultLoader : DefaultLoader {

	companion object {

		private val IMAGE = ByteArrayImageData(intArrayOf(
			128, 0, 254, 0, 0, 0, 128, 0, 254, 0, 0, 0,
			0, 0, 0, 128, 0, 254, 0, 0, 0, 128, 0, 254,
			128, 0, 254, 0, 0, 0, 128, 0, 254, 0, 0, 0,
			0, 0, 0, 128, 0, 254, 0, 0, 0, 128, 0, 254,
		).map { it.toByte() }
			.toByteArray(), PixelFormat.RGB, 4, 4)
//		private val IMAGE =
//			ImageDataDefaultLoader::class.java.classLoader.getResourceAsStream("texture/default.jpg").use {
//				ImageIOImageLoader.IMAGE_DATA_LOADER.loadImage(it)
//			}
//		private val IMAGE = ImageData.BLACK
	}

	override fun <T : Any> load(context: DefaultLoader.Context, type: TypeInfo<T>): T? {
		if(TypeUtil.isExtends(type, ImageData::class.java)) {
			return IMAGE as T
		}
		return null
	}
}
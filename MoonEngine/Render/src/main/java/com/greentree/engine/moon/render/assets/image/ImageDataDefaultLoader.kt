package com.greentree.engine.moon.render.assets.image

import com.greentree.commons.image.image.ImageData
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader

class ImageDataDefaultLoader : DefaultLoader {

	override fun <T : Any> load(context: DefaultLoader.Context, type: TypeInfo<T>, key: AssetKeyType): T? {
		if(TypeUtil.isExtends(ImageData::class.java, type) && key === AssetKeyType.DEFAULT) {
//			return ByteArrayImageData(intArrayOf(
//				128, 0, 254, 0, 0, 0, 128, 0, 254, 0, 0, 0,
//				0, 0, 0, 128, 0, 254, 0, 0, 0, 128, 0, 254,
//				128, 0, 254, 0, 0, 0, 128, 0, 254, 0, 0, 0,
//				0, 0, 0, 128, 0, 254, 0, 0, 0, 128, 0, 254,
//			).map { it.toByte() }
//				.toByteArray(), PixelFormat.RGB, 4, 4) as T
//			ImageDataDefaultLoader::class.java.classLoader.getResourceAsStream("texture/default.jpg").use {
//				return ImageIOImageLoader.IMAGE_DATA_LOADER.loadImage(it) as T
//			}
			return ImageData.BLACK as T
		}
		return null
	}
}
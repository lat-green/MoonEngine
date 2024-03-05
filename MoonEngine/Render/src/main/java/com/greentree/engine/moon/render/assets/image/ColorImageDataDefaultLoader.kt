package com.greentree.engine.moon.render.assets.image

import com.greentree.commons.image.Color
import com.greentree.commons.image.image.ColorImageData
import com.greentree.commons.image.image.ImageData
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader
import com.greentree.engine.moon.assets.serializator.loader.loadDefault

class ColorImageDataDefaultLoader : DefaultLoader {

	override fun <T : Any> load(context: DefaultLoader.Context, type: TypeInfo<T>): T? {
		if(TypeUtil.isExtends(ImageData::class.java, type)) {
			val color = context.loadDefault() ?: Color.white
			return ColorImageData(color) as T
		}
		return null
	}
}
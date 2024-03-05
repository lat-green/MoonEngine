package com.greentree.engine.moon.render.assets.image

import com.greentree.commons.image.Color
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader

class ColorDefaultLoader : DefaultLoader {

	override fun <T : Any> load(context: DefaultLoader.Context, type: TypeInfo<T>): T? {
		if(TypeUtil.isExtends(Color::class.java, type)) {
			return Color.black as T
		}
		return null
	}
}
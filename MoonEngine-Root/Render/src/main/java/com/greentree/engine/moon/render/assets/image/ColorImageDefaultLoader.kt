package com.greentree.engine.moon.render.assets.image

import com.greentree.commons.image.Color
import com.greentree.commons.image.image.ColorImageData
import com.greentree.commons.image.image.ImageData
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load

class ColorImageDefaultLoader : DefaultLoader {

	override fun <T : Any> load(context: DefaultLoader.Context, type: TypeInfo<T>, key: AssetKeyType): T? {
		if(TypeUtil.isExtends(ImageData::class.java, type)) {
			val color = context.load<Color>(key)
			return color?.let { ColorImageData(it) as T }
		}
		return null
	}
}
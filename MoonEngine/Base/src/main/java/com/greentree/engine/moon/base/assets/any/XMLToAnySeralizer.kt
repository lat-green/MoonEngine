package com.greentree.engine.moon.base.assets.any

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.base.assets.scene.adapters.ObjectXMLBuilder

object XMLToAnyAssetLoader : AssetLoader {

	val builder = ObjectXMLBuilder()

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
		val xml = context.load<XMLElement>(key)
		builder.newInstance(type, xml).use {
			return it.value()
		}
	}
}
package com.greentree.engine.moon.base.assets.any

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.base.assets.scene.adapters.ObjectXMLBuilder

object XMLToAnyAssetLoader : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		val xml = context.load<XMLElement>(key)
		return xml.map(XMLToAny(type))
	}

	private data class XMLToAny<T : Any>(
		val type: TypeInfo<T>,
	) : Value1Function<XMLElement, T> {

		val builder = ObjectXMLBuilder()

		override fun apply(value: XMLElement): T {
			builder.newInstance(type, value).use {
				return it.value()
			}
		}
	}
}
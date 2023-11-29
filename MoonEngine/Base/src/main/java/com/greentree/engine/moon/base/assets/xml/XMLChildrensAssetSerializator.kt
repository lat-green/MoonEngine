package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object XMLChildrensAssetSerializator : AssetSerializator<Iterable<XMLElement>> {

	override fun load(context: AssetLoader.Context, key: AssetKey): Asset<Collection<XMLElement>> {
		if(key is XMLChildrenAssetKey) {
			val xml = context.load<XMLElement>(key.xml)
			return xml.map(XMLToChildrens(key.name))
		}
		throw NotSupportedKeyType
	}

	private class XMLToChildrens(val name: String) : Value1Function<XMLElement, Collection<XMLElement>> {

		override fun apply(value: XMLElement): Collection<XMLElement> = value.getChildrens(name)

		override fun toString() = "XMLToChildrens"
	}
}
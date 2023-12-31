package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object XMLChildrenAssetSerializator : AssetSerializator<Iterable<XMLElement>> {

	override fun load(context: AssetLoader.Context, key: AssetKey): AssetProvider<Collection<XMLElement>> {
		if(key is XMLChildrenAssetKey) {
			val xml = context.load<XMLElement>(key.xml)
			return xml.map(XMLToChildren(key.name))
		}
		throw NotSupportedKeyType
	}

	private class XMLToChildren(val name: String) : Value1Function<XMLElement, Collection<XMLElement>> {

		override fun apply(value: XMLElement): Collection<XMLElement> = value.getChildrens(name)

		override fun toString() = "XMLToChildrens"
	}
}
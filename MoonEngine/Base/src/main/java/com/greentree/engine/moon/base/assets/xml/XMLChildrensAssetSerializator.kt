package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load

object XMLChildrensAssetSerializator : AssetSerializator<Iterable<XMLElement>> {

	override fun load(context: AssetManager, key: AssetKey): Asset<Iterable<XMLElement>>? {
		if(key is XMLChildrenAssetKey) {
			val resource = context.load<XMLElement>(key.xml)
			return resource.map(XMLToChildrens(key.name))
		}
		return null
	}

	private class XMLToChildrens(val name: String) : Value1Function<XMLElement, Iterable<XMLElement>> {

		override fun apply(value: XMLElement): Iterable<XMLElement> = value.getChildrens(name)

		override fun toString() = "XMLToChildrens"
	}
}
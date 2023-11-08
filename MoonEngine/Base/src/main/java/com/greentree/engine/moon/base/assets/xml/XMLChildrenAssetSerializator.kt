package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load

object XMLChildrenAssetSerializator : AssetSerializator<XMLElement> {

	override fun load(context: AssetManager, key: AssetKey): Asset<XMLElement>? {
		if(key is XMLChildrenAssetKey) {
			val resource = context.load<XMLElement>(key.xml)
			return resource.map(XMLToChildren(key.name))
		}
		return null
	}

	private class XMLToChildren(val name: String) : Value1Function<XMLElement, XMLElement> {

		override fun apply(value: XMLElement) = value.getChildren(name)

		override fun toString() = "XMLToChildren"
	}
}
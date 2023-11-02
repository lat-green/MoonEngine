package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.xml.SAXXMLParser
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.kernel.use

object ResourceToXMLAssetSerializator : AssetSerializator<XMLElement> {

	override fun load(context: AssetManager, key: AssetKey): Asset<XMLElement> {
		val resource = context.load<Resource>(key)
		return resource.map(ResourceToXML)
	}

	private object ResourceToXML : Value1Function<Resource, XMLElement> {

		override fun apply(resource: Resource): XMLElement {
			resource.open().use {
				return SAXXMLParser.parse(it)
			}
		}

		override fun toString() = "TextToXML"
	}
}
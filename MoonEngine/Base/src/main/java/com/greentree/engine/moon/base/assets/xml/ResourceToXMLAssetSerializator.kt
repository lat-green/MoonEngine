package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.xml.SAXXMLParser
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object ResourceToXMLAssetSerializator : AssetSerializator<XMLElement> {

	override fun load(context: AssetLoader.Context, key: AssetKey): AssetProvider<XMLElement> {
		val resource = context.load<Resource>(key)
		return resource.map(ResourceToXML)
	}

	private object ResourceToXML : Value1Function<Resource, XMLElement> {

		override fun apply(resource: Resource): XMLElement {
			resource.open().use {
				return SAXXMLParser.parse(it)
			}
		}

		override fun toString() = "ResourceToXML"
	}
}
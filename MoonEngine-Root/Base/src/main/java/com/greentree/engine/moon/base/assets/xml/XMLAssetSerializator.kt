package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.xml.SAXXMLParser
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import java.io.ByteArrayInputStream

class XMLAssetSerializator : AssetSerializator<XMLElement> {

	override fun load(context: AssetManager, key: AssetKey): Asset<XMLElement> {
		val text = context.load<String>(key)
		return text.map(XMLTextFunction)
	}

	private object XMLTextFunction : Value1Function<String, XMLElement> {

		override fun apply(text: String): XMLElement {
			return ByteArrayInputStream(text.toByteArray()).use { SAXXMLParser.parse(it) }
		}
	}
}
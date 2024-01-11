package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.xml.SAXXMLParser
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object TextToXMLAssetSerializator : AssetSerializator<XMLElement> {

	override fun load(context: AssetLoader.Context, key: AssetKey): AssetProvider<XMLElement> {
		val text = context.load<String>(key)
		return text.map(TextToXML)
	}

	private object TextToXML : Value1Function<String, XMLElement> {

		override fun apply(text: String): XMLElement {
			return SAXXMLParser.parse(text.byteInputStream())
		}

		override fun toString() = "TextToXML"
	}
}
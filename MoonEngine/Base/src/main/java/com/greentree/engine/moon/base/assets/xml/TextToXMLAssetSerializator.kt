package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.xml.SAXXMLParser
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

object TextToXMLAssetSerializator : AssetSerializator<XMLElement> {

	override fun load(context: AssetLoader.Context, key: AssetKey): XMLElement {
		val text = context.load<String>(key)
		return SAXXMLParser.parse(text.byteInputStream())
	}
}
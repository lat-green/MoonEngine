package com.greentree.engine.moon.base.assets.xml

import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

object XMLChildrenAssetSerializator : AssetSerializator<Iterable<XMLElement>> {

	override fun load(context: AssetLoader.Context, key: AssetKey): Collection<XMLElement> {
		if(key is XMLChildrenAssetKey) {
			val xml = context.load<XMLElement>(key.xml)
			return xml.getChildrens(key.name)
		}
		throw NotSupportedKeyType
	}
}
package com.greentree.engine.moon.base.assets.text

import com.greentree.commons.util.string.RefStringBuilder
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

object RefStringBuilderInsertAssetSerializator : AssetSerializator<String> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): String {
		if(key is RefStringBuilderAssetKey) {
			val text = manager.load<RefStringBuilder>(key.text)
			return text.toString(key.parameters)
		}
		throw NotSupportedKeyType
	}
}
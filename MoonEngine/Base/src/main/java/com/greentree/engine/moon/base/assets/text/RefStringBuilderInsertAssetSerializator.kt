package com.greentree.engine.moon.base.assets.text

import com.greentree.commons.util.string.RefStringBuilder
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object RefStringBuilderInsertAssetSerializator : AssetSerializator<String> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<String> {
		if(key is RefStringBuilderAssetKey) {
			val text = manager.load<RefStringBuilder>(key.text)
			return text.map(RefStringBuilderInsert(key.parameters))
		}
		throw NotSupportedKeyType
	}

	private class RefStringBuilderInsert(val params: Map<String, String>) :
		Value1Function<RefStringBuilder, String> {

		override fun apply(value: RefStringBuilder) = value.toString(params)
	}
}
package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.NamedAssetKey
import com.greentree.engine.moon.assets.location.AssetLocation

class NamedAssetLoader(private val location: AssetLocation) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		if(key is NamedAssetKey) {
			val realKey = location.getKey(key.name)
			return context.load(type, realKey)
		}
		throw NotSupportedKeyType
	}
}
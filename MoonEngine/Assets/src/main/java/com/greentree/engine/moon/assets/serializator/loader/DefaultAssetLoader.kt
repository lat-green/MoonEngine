package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.DefaultAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.DefaultAssetKey

class DefaultAssetLoader : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		if(key is DefaultAssetKey) {
			val values = key.keys.map { context.load(type, it) }
			return DefaultAsset.newAsset(values)
		}
		throw NotSupportedKeyType
	}
}
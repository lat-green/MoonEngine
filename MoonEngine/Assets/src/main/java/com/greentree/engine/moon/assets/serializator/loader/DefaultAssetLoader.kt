package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.DefaultAssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.DefaultAssetProvider

class DefaultAssetLoader : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): AssetProvider<T> {
		if(key is DefaultAssetKey) {
			val values = key.keys.map { context.load(type, it) }
			return DefaultAssetProvider.newAsset(values)
		}
		throw NotSupportedKeyType
	}
}
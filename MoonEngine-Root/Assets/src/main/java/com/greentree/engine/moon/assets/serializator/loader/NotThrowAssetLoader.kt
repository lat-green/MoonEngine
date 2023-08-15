package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ThrowAsset
import com.greentree.engine.moon.assets.key.AssetKey

class NotThrowAssetLoader(private val origin: AssetLoader) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		return try {
			origin.load(context, type, key)
		} catch(e: Exception) {
			ThrowAsset(e)
		}
	}
}

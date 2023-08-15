package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ThrowAsset
import com.greentree.engine.moon.assets.key.AssetKey

class NotNullAssetLoader(private val origin: AssetLoader) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		return origin.load(context, type, key) ?: ThrowAsset(NullPointerException("loader: $origin return null"))
	}
}

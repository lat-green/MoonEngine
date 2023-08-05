package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.NotValidAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager

class NotNullAssetLoader(private val origin: AssetLoader) : AssetLoader {

	override fun <T : Any> load(manager: AssetManager, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		return origin.load(manager, type, key) ?: NotValidAsset.instance()
	}
}

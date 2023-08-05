package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager

class MultiAssetLoader(private val loaders: Iterable<AssetLoader>) : AssetLoader {

	override fun <T : Any> load(manager: AssetManager, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		for(loader in loaders) {
			val result = loader.load(manager, type, key)
			if(result != null)
				return result
		}
		return null
	}
}

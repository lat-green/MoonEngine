package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyAndType
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ConstAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey

class ResultAssetLoader : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		if(key is ResultAssetKey) {
			val result = key.result
			if(type.isInstance(result))
				return ConstAsset(result as T)
			throw NotSupportedKeyAndType
		}
		throw NotSupportedKeyType
	}
}
package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyAndType
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.ConstAssetProvider

class ResultAssetLoader : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): AssetProvider<T> {
		if(key is ResultAssetKey) {
			val result = key.result
			if(type.isInstance(result))
				return ConstAssetProvider(result as T)
			throw NotSupportedKeyAndType
		}
		throw NotSupportedKeyType
	}
}
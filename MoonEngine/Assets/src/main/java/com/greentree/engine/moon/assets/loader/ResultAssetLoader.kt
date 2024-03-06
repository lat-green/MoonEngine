package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.exception.NotSupportedKeyAndType
import com.greentree.engine.moon.assets.exception.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey

data object ResultAssetLoader : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
		if(key is ResultAssetKey) {
			val result = key.result
			if(type.isInstance(result))
				return result as T
			throw NotSupportedKeyAndType
		}
		throw NotSupportedKeyType
	}
}
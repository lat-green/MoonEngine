package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKeyType

class MultiDefaultAssetLoader(private val loaders: Iterable<DefaultLoader>) : DefaultLoader {

	override fun <T : Any> load(context: DefaultLoader.Context, type: TypeInfo<T>, key: AssetKeyType): T? {
		for(loader in loaders) {
			val result = loader.load(context, type, key)
			if(result != null)
				return result
		}
		return null
	}
}
package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.util.exception.MultiException
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey

class MultiAssetLoader(private val loaders: Iterable<AssetLoader>) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		val exceptions = mutableListOf<RuntimeException>()
		for(loader in loaders) {
			try {
				val result = loader.load(context, type, key)
				if(result != null)
					return result
			} catch(e: RuntimeException) {
				exceptions.add(e)
			}
		}
		if(exceptions.isEmpty())
			return null
		if(exceptions.size == 1)
			throw exceptions.first()
		throw MultiException(exceptions)
	}
}

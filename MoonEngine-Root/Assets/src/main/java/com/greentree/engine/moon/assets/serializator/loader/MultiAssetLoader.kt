package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ThrowAsset
import com.greentree.engine.moon.assets.asset.isValid
import com.greentree.engine.moon.assets.key.AssetKey

class MultiAssetLoader(private val loaders: Iterable<AssetLoader>) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		val exceptions = mutableListOf<Exception>()
		val results = mutableListOf<Asset<T>>()
		for(loader in loaders) {
			try {
				val result = loader.load(context, type, key)
				if(result != null)
					if(result is ThrowAsset)
						exceptions.add(result.exception)
					else
						results.add(result)
			} catch(e: Exception) {
				exceptions.add(e)
			}
		}
		for(it in results)
			if(it.isValid)
				return it
		for(it in results)
			if(!it.isConst())
				return it
		if(exceptions.size == 1)
			return ThrowAsset(exceptions.first())
		val exception = RuntimeException("no one loader can\'t load type: $type, key: $key, loaders: $loaders")
		for(e in exceptions)
			exception.addSuppressed(e)
		return ThrowAsset(exception)
	}

	override fun toString(): String {
		return "MultiLoader($loaders)"
	}
}

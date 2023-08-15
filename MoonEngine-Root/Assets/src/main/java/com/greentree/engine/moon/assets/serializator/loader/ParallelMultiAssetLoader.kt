package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.util.exception.MultiException
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class ParallelMultiAssetLoader(private val loaders: Iterable<AssetLoader>, private val executor: ExecutorService) :
	AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		val results = mutableListOf<Future<Asset<T>?>>()
		for(loader in loaders)
			results.add(executor.submit(Callable { loader.load(context, type, key) }))
		val exceptions = mutableListOf<RuntimeException>()
		for(result in results) {
			try {
				val result = result.get()
				if(result != null)
					return result
			} catch(e: ExecutionException) {
				exceptions.add(e.cause as RuntimeException)
			}
		}
		if(exceptions.isEmpty())
			return null
		if(exceptions.size == 1)
			throw exceptions.first()
		throw MultiException(exceptions)
	}
}

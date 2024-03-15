package com.greentree.engine.moon.assets.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.FutureAsset
import com.greentree.engine.moon.assets.cache.HashMapCacheFactory
import com.greentree.engine.moon.assets.cache.WeakHashMapCacheFactory
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.CacheAssetLoader
import com.greentree.engine.moon.assets.loader.DefaultAssetLoader
import com.greentree.engine.moon.assets.loader.MultiAssetLoader
import com.greentree.engine.moon.assets.loader.ResultAssetLoader
import org.apache.logging.log4j.LogManager
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

data class ParallelAssetManager(
	private val executor: ExecutorService = Executors.newCachedThreadPool(),
) : MutableAssetManager {

	constructor(threadFactory: ThreadFactory) : this(
		Executors.newCachedThreadPool(
			threadFactory
		)
	)

	private val loaders = mutableListOf<AssetLoader>()
	private val multiLoaders =
		CacheAssetLoader(
			MultiAssetLoader(loaders),
			WeakHashMapCacheFactory
		)

	init {
		addLoader(DefaultAssetLoader)
		addLoader(ResultAssetLoader)
	}

	override fun addLoader(loader: AssetLoader) {
		loaders.add(loader)
	}

	override fun <T : Any> loadAsset(type: TypeInfo<T>, key: AssetKey): Asset<T> {
		val type = type.boxing
		val result = executor.submit(Callable {
			multiLoaders.load(this, type, key)
		})
		return FutureAsset(result)
	}

	companion object {

		private val LOG = LogManager.getLogger(
			ParallelAssetManager::class.java
		)
	}
}

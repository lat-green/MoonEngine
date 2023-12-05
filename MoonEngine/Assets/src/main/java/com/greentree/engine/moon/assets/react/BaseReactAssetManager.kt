package com.greentree.engine.moon.assets.react

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.react.loader.MultiReactAssetLoader
import com.greentree.engine.moon.assets.react.loader.ReactAssetLoader
import com.greentree.engine.moon.assets.serializator.manager.chain.Chain
import com.greentree.engine.moon.assets.serializator.manager.chain.ChainHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

class BaseReactAssetManager : ReactAssetManager {

	private val loaders = mutableListOf<ReactAssetLoader>()
	private val multiLoaders = MultiReactAssetLoader(loaders)

	override fun addLoader(loader: ReactAssetLoader) {
		loaders.add(loader)
	}

	override fun build(handler: ChainHandler): Chain {
		TODO("Not yet implemented")
	}

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey) = runBlocking {
		ReactAsset(multiLoaders, object : ReactAssetLoaderContext {
			override suspend fun <T : Any> load(
				context: ReactAssetLoader.Context,
				type: TypeInfo<T>,
				key: AssetKey,
			) = multiLoaders.load(context, type, key)
		}, type, key)
	}

	interface ReactAssetLoaderContext {

		suspend fun <T : Any> load(context: ReactAssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T
	}

	private class ReactAssetLoaderContextImpl(
		val context: ReactAssetLoaderContext,
		reactContext: ReactContext,
		coroutineScope: CoroutineScope,
	) : ReactAssetLoader.Context, ReactContext by reactContext, CoroutineScope by coroutineScope {

		override suspend fun <T : Any> load(type: TypeInfo<T>, key: AssetKey) = context.load(this, type, key)
	}

	class ReactAsset<T : Any>(
		val loader: ReactAssetLoader,
		val context: ReactAssetLoaderContext,
		val type: TypeInfo<T>,
		val key: AssetKey,
	) : Asset<T> {

		private val refs = mutableListOf<Pair<Ref<*>, () -> Unit>>()
		private val onRefresh = {
			lastModified = System.currentTimeMillis()
		}
		override val value
			get() = runBlocking {
				val ctx = SmartReactContext(refs, onRefresh)
				loader.load(
					ReactAssetLoaderContextImpl(
						context,
						ctx,
						this
					), type, key
				)
			}
		override var lastModified = System.currentTimeMillis()
			private set
	}
}

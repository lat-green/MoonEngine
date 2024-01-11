package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.ConstAssetProvider
import com.greentree.engine.moon.assets.provider.ReduceAssetProvider
import com.greentree.engine.moon.assets.serializator.manager.chain.Chain
import com.greentree.engine.moon.assets.serializator.manager.chain.ChainHandler
import java.lang.Thread.*
import java.util.concurrent.Executors

private var ID = 0
val EXECUTOR = Executors.newFixedThreadPool(
	Runtime.getRuntime().availableProcessors()
) {
	val thread = Thread(it, "async-asset-${ID++}")
	thread.isDaemon = true
	thread.priority = MAX_PRIORITY
	thread
}

data object AsyncHandler : ChainHandler {

	override fun <T : Any> load(chain: Chain, type: TypeInfo<T>, key: AssetKey): AssetProvider<T> {
		val res = chain.loadCache(type, key)
		if(res != null)
			return res
		val default = chain.loadDefault(type, key.type()) ?: return chain.load(type, key)
		val result = ReduceAssetProvider(ConstAssetProvider(default))
		EXECUTOR.submit {
			try {
				result.origin = chain.load(type, key)
			} catch(e: Exception) {
				e.printStackTrace()
			}
		}
		return result
	}
}

fun <T : Any> ChainAssetManager.loadAsyncProvider(type: TypeInfo<T>, key: AssetKey) =
	build(AsyncHandler).load(type, key)

fun <T : Any> ChainAssetManager.loadAsync(type: TypeInfo<T>, key: AssetKey) = wrap(build(AsyncHandler).load(type, key))

fun <T : Any> ChainAssetManager.loadAsync(type: TypeInfo<T>, key: Any) = loadAsync(type, ResultAssetKey(key))
fun <T : Any> ChainAssetManager.loadAsync(type: TypeInfo<T>, resource: String) =
	loadAsync(type, ResourceAssetKey(resource))

fun <T : Any> ChainAssetManager.loadAsync(cls: Class<T>, key: AssetKey) =
	loadAsync(TypeInfoBuilder.getTypeInfo(cls), key)

fun <T : Any> ChainAssetManager.loadAsync(cls: Class<T>, key: Any) = loadAsync(TypeInfoBuilder.getTypeInfo(cls), key)
fun <T : Any> ChainAssetManager.loadAsync(cls: Class<T>, resource: String) =
	loadAsync(TypeInfoBuilder.getTypeInfo(cls), resource)

inline fun <reified T : Any> ChainAssetManager.loadAsync(key: AssetKey) =
	loadAsync(TypeInfoBuilder.getTypeInfo(T::class.java), key)

inline fun <reified T : Any> ChainAssetManager.loadAsync(key: Any) =
	loadAsync(TypeInfoBuilder.getTypeInfo(T::class.java), key)

inline fun <reified T : Any> ChainAssetManager.loadAsync(resource: String) =
	loadAsync(TypeInfoBuilder.getTypeInfo(T::class.java), resource)

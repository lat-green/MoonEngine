package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ConstAsset
import com.greentree.engine.moon.assets.asset.ReduceAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
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

fun <T : Any> AssetManager.loadAsync(type: TypeInfo<T>, key: AssetKey): Asset<T> {
	return object : AssetLoader.Context {
		override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> {
			val res = loadCache(type, key)
			if(res != null)
				return res
			val default = loadDefault(type, key.type()) ?: return this@loadAsync.load(this, type, key)
			val result = ReduceAsset(ConstAsset(default))
			EXECUTOR.submit {
				try {
					result.asset = this@loadAsync.load(this, type, key)
				} catch(e: Exception) {
					e.printStackTrace()
				}
			}
			return result
		}
	}.load(type, key)
}

fun <T : Any> AssetManager.loadAsync(cls: Class<T>, key: Any): Asset<T> {
	return loadAsync(cls, ResultAssetKey(key))
}

fun <T : Any> AssetManager.loadAsync(type: TypeInfo<T>, key: Any): Asset<T> {
	return loadAsync(type, ResultAssetKey(key))
}

fun <T : Any> AssetManager.loadAsync(cls: Class<T>, key: AssetKey): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(cls)
	return loadAsync(type, key)
}

fun <T : Any> AssetManager.loadAsync(cls: Class<T>, resource: String): Asset<T> {
	return loadAsync(cls, ResourceAssetKey(resource))
}

fun <T : Any> AssetManager.loadAsync(type: TypeInfo<T>, resource: String): Asset<T> {
	return loadAsync(type, ResourceAssetKey(resource))
}

inline fun <reified T : Any> AssetManager.loadAsync(key: Any?): Asset<T> {
	return loadAsync(ResultAssetKey(key))
}

inline fun <reified T : Any> AssetManager.loadAsync(key: AssetKey): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return loadAsync(type, key)
}

inline fun <reified T : Any> AssetManager.loadAsync(resource: String): Asset<T> {
	return loadAsync(ResourceAssetKey(resource))
}
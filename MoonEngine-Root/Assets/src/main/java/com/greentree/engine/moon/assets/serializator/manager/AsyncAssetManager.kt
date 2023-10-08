package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ConstAsset
import com.greentree.engine.moon.assets.asset.ReduceAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import java.lang.Thread.*
import java.util.concurrent.Executors

interface AsyncAssetManager : AssetManager {

	fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T>
}

val EXECUTOR = Executors.newFixedThreadPool(
	Runtime.getRuntime().availableProcessors()
) {
	val thread = Thread(it, "async-asset")
	thread.isDaemon = true
	thread.priority = MAX_PRIORITY
	thread
}

fun <T : Any> AsyncAssetManager.loadAsync(type: TypeInfo<T>, key: AssetKey): Asset<T> {
	return object : AssetLoader.Context {
		override fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType) =
			this@loadAsync.loadDefault(type, key)

		override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> {
			val default = loadDefault(type, key.type()) ?: return this@loadAsync.load(this, type, key)
			val result = ReduceAsset(ConstAsset(default))
			EXECUTOR.submit {
				result.asset = this@loadAsync.load(this, type, key)
			}
			return result
		}
	}.load(type, key)
}

fun <T : Any> AsyncAssetManager.loadAsync(cls: Class<T>, key: Any): Asset<T> {
	return loadAsync(cls, ResultAssetKey(key))
}

fun <T : Any> AsyncAssetManager.loadAsync(type: TypeInfo<T>, key: Any): Asset<T> {
	return loadAsync(type, ResultAssetKey(key))
}

fun <T : Any> AsyncAssetManager.loadAsync(cls: Class<T>, key: AssetKey): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(cls)
	return loadAsync(type, key)
}

fun <T : Any> AsyncAssetManager.loadAsync(cls: Class<T>, resource: String): Asset<T> {
	return loadAsync(cls, ResourceAssetKey(resource))
}

fun <T : Any> AsyncAssetManager.loadAsync(type: TypeInfo<T>, resource: String): Asset<T> {
	return loadAsync(type, ResourceAssetKey(resource))
}

inline fun <reified T : Any> AsyncAssetManager.loadAsync(key: Any?): Asset<T> {
	return loadAsync(ResultAssetKey(key))
}

inline fun <reified T : Any> AsyncAssetManager.loadAsync(key: AssetKey): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return loadAsync(type, key)
}

inline fun <reified T : Any> AsyncAssetManager.loadAsync(resource: String): Asset<T> {
	return loadAsync(ResourceAssetKey(resource))
}
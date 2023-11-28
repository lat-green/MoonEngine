package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import org.apache.logging.log4j.MarkerManager
import kotlin.reflect.KClass

interface AssetManager {

	fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType): T?

	fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T>
	fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T>
	fun <T : Any> loadCache(type: TypeInfo<T>, key: AssetKey): Asset<T>? = null

	fun <T : Any> load(cls: Class<T>, key: Any): Asset<T> {
		return load(cls, ResultAssetKey(key))
	}

	fun <T : Any> load(type: TypeInfo<T>, key: Any): Asset<T> {
		return load(type, ResultAssetKey(key))
	}

	fun <T : Any> load(cls: Class<T>, key: AssetKey): Asset<T> {
		val type = TypeInfoBuilder.getTypeInfo(cls)
		return load(type, key)
	}

	fun <T : Any> load(cls: Class<T>, resource: String): Asset<T> {
		return load(cls, ResourceAssetKey(resource))
	}

	fun <T : Any> load(type: TypeInfo<T>, resource: String): Asset<T> {
		return load(type, ResourceAssetKey(resource))
	}

	companion object {

		@JvmField
		val ASSETS = MarkerManager.getMarker("assets")!!
	}
}

fun AssetManager.canLoad(type: TypeInfo<*>, key: AssetKey) = load(type, key).isValid()
fun AssetManager.canLoad(type: Class<*>, key: AssetKey) = canLoad(TypeInfoBuilder.getTypeInfo(type), key)
fun AssetManager.canLoad(type: KClass<*>, key: AssetKey) = canLoad(type.java, key)
inline fun <reified T> AssetManager.canLoad(key: AssetKey) = canLoad(T::class.java, key)

inline fun <reified T : Any> AssetManager.load(key: Any?): Asset<T> {
	return load(ResultAssetKey(key))
}

inline fun <reified T : Any> AssetManager.load(key: AssetKey): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return load(type, key)
}

inline fun <reified T : Any> AssetManager.load(resource: String): Asset<T> {
	return load(ResourceAssetKey(resource))
}

inline fun <reified T : Any> AssetManager.loadDefault(key: AssetKey): T? {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return loadDefault(type, key.type())
}

inline fun <reified T : Any> AssetManager.loadDefault(): T? {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return loadDefault(type, AssetKeyType.DEFAULT)
}

fun <T : Any> AssetManager.loadDefault(type: TypeInfo<T>, key: AssetKey): T? {
	return loadDefault(type, key.type())
}

fun <T : Any> AssetManager.loadDefault(type: TypeInfo<T>): T? {
	return loadDefault(type, AssetKeyType.DEFAULT)
}

package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey

interface AssetLoader {

	fun <T : Any> load(context: Context, type: TypeInfo<T>, key: AssetKey): Asset<T>?

	interface Context {

		fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T>

		fun <T : Any> load(cls: Class<T>, key: AssetKey): Asset<T> {
			val type = TypeInfoBuilder.getTypeInfo(cls)
			return load(type, key)
		}
	}
}

fun <T : Any> AssetLoader.Context.load(cls: Class<T>, key: AssetKey) = load(TypeInfoBuilder.getTypeInfo(cls), key)
fun <T : Any> AssetLoader.Context.load(cls: Class<T>, key: Any) = load(TypeInfoBuilder.getTypeInfo(cls), key)
fun <T : Any> AssetLoader.Context.load(cls: Class<T>, resource: String) =
	load(TypeInfoBuilder.getTypeInfo(cls), resource)

inline fun <reified T : Any> AssetLoader.Context.load(key: AssetKey): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return load(type, key)
}

inline fun <reified T : Any> AssetLoader.Context.load(key: Any): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return load(type, key)
}

inline fun <reified T : Any> AssetLoader.Context.load(resource: String): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return load(type, resource)
}

fun <T : Any> AssetLoader.Context.load(type: TypeInfo<T>, key: Any) = load(type, ResultAssetKey(key))
fun <T : Any> AssetLoader.Context.load(type: TypeInfo<T>, resource: String) = load(type, ResourceAssetKey(resource))
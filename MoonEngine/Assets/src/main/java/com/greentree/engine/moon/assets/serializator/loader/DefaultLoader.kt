package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType

interface DefaultLoader {

	fun <T : Any> load(context: Context, type: TypeInfo<T>, key: AssetKeyType): T?

	interface Context {

		fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType): T?
	}
}

inline fun <reified T : Any> DefaultLoader.Context.loadDefault(key: AssetKeyType): T? {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return loadDefault(type, key)
}

inline fun <reified T : Any> DefaultLoader.Context.loadDefault(key: AssetKey): T? {
	return loadDefault<T>(key.type())
}

inline fun <reified T : Any> DefaultLoader.Context.loadDefault(): T? {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return loadDefault(type, AssetKeyType.DEFAULT)
}

fun <T : Any> DefaultLoader.Context.loadDefault(type: TypeInfo<T>, key: AssetKey): T? {
	return loadDefault(type, key.type())
}

fun <T : Any> DefaultLoader.Context.loadDefault(type: TypeInfo<T>): T? {
	return loadDefault(type, AssetKeyType.DEFAULT)
}
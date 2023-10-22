package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType

interface DefaultLoader {

	fun <T : Any> load(context: Context, type: TypeInfo<T>, key: AssetKeyType): T?

	interface Context {

		fun <T : Any> load(type: TypeInfo<T>, key: AssetKeyType): T?
	}
}

inline fun <reified T : Any> DefaultLoader.Context.load(key: AssetKeyType): T? {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return load(type, key)
}

inline fun <reified T : Any> DefaultLoader.Context.load(key: AssetKey): T? {
	return load<T>(key.type())
}

inline fun <reified T : Any> DefaultLoader.Context.load(): T? {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return load(type, AssetKeyType.DEFAULT)
}

fun <T : Any> DefaultLoader.Context.load(type: TypeInfo<T>, key: AssetKey): T? {
	return load(type, key.type())
}

fun <T : Any> DefaultLoader.Context.load(type: TypeInfo<T>): T? {
	return load(type, AssetKeyType.DEFAULT)
}
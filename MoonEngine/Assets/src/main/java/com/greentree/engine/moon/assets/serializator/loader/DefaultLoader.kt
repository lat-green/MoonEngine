package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import kotlin.reflect.KClass

interface DefaultLoader {

	fun <T : Any> load(context: Context, type: TypeInfo<T>): T?

	interface Context {

		fun <T : Any> loadDefault(type: TypeInfo<T>): T?
	}
}

inline fun <reified T : Any> DefaultLoader.Context.loadDefault(): T? {
	val type = getTypeInfo(T::class.java)
	return loadDefault(type)
}

fun <T : Any> DefaultLoader.Context.loadDefault(cls: Class<T>) = loadDefault(getTypeInfo(cls))
fun <T : Any> DefaultLoader.Context.loadDefault(cls: KClass<T>) = loadDefault(getTypeInfo(cls))
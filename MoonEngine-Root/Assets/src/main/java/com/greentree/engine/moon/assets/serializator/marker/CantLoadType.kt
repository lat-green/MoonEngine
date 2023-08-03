package com.greentree.engine.moon.assets.serializator.marker

import com.greentree.commons.util.function.SaveFunction
import kotlin.reflect.KClass

class CantLoadType private constructor(cls: Class<*>) : AbstractAssetSerializatorMarker() {

	companion object {

		private val cache = toCache { cls: Class<*> -> CantLoadType(cls) }

		fun get(cls: KClass<*>) = get(cls.java)
		fun get(cls: Class<*>) = cache(cls)
		inline fun <reified T> get() = get(T::class)
	}
}

private fun <T, R> toCache(function: (T) -> R): (T) -> R {
	if(function is SaveFunction<*, *>)
		return function
	val cacheFunction = object : SaveFunction<T, R>() {
		override fun create(value: T) = function(value)
	}
	return { cacheFunction.apply(it) }
}
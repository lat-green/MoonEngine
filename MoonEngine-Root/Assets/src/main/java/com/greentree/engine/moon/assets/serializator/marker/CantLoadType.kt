package com.greentree.engine.moon.assets.serializator.marker

import kotlin.reflect.KClass

class CantLoadType private constructor(cls: Class<*>) : AbstractAssetSerializatorMarker() {
	companion object {

		fun get(cls: KClass<*>) = CantLoadType(cls.java)
		fun get(cls: Class<*>) = CantLoadType(cls)
		inline fun <reified T> get() = get(T::class)
	}
}

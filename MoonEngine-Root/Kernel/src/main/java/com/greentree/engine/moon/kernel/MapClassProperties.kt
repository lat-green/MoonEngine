package com.greentree.engine.moon.kernel

import java.util.*

open class MapClassProperties<P : Any> : ClassProperties<P> {

	private val map: MutableMap<Class<out P>, P> = HashMap(32)

	override fun iterator(): Iterator<P> {
		return map.values.iterator()
	}

	override fun <T : P> getProperty(cls: Class<T>): Optional<T> {
		return Optional.ofNullable(map[cls] as T?)
	}

	override fun add(property: P) {
		val cls = property::class.java
		require(!contains(cls)) { "add already contains property $cls" }
		map[cls] = property
	}

	override fun <T : P> remove(propertyClass: Class<T>): T {
		require(contains(propertyClass)) { "remove not contains property $propertyClass" }
		return map.remove(propertyClass) as T
	}

	override fun clear() {
		map.clear()
	}
}
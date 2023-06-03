package com.greentree.engine.moon.kernel

import java.util.*

interface ClassProperties<P : Any> : Iterable<P> {

	operator fun <T : P> get(cls: Class<T>): T {
		val property = try {
			getProperty(cls)
		} catch (e: Exception) {
			throw IllegalArgumentException("get property of class: $cls", e)
		}
		return property.orElseThrow { IllegalArgumentException("not found property of class: $cls") }
	}

	operator fun contains(cls: Class<out P>): Boolean {
		return getProperty(cls).isPresent
	}

	fun <T : P> getProperty(cls: Class<T>): Optional<T>

	fun add(property: P)
	fun <T : P> remove(propertyClass: Class<T>): T
	fun clear()
}
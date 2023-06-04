package com.greentree.engine.moon.kernel

import java.util.*

interface ReadOnlyClassProperties<P> : Iterable<P> {

	operator fun <T : P> get(cls: Class<out T>): T {
		val property = try {
			getProperty(cls)
		} catch(e: Exception) {
			throw IllegalArgumentException("get property of class: \$cls", e)
		}
		return property.orElseThrow { IllegalArgumentException("not found property of class: \$cls") }
	}

	fun <T : P> getProperty(cls: Class<out T>): Optional<T>
	operator fun contains(cls: Class<out P>): Boolean {
		return getProperty(cls).isPresent
	}
}
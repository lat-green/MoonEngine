package com.greentree.engine.moon.kernel

interface ClassProperties<P> : ReadOnlyClassProperties<P> {

	fun add(property: P)
	fun <T : P> remove(propertyClass: Class<out T>): T
	fun clear()
}
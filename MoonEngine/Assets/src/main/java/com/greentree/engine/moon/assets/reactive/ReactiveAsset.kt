package com.greentree.engine.moon.assets.reactive

interface ReactiveAsset<out T : Any> {

	val value: T

	fun addListener(listener: (value: T, lastValue: T) -> Unit)
	fun removeListener(listener: (value: T, lastValue: T) -> Unit)
}

operator fun <T : Any> ReactiveAsset<T>.plusAssign(listener: (value: T, lastValue: T) -> Unit) {
	addListener(listener)
}

operator fun <T : Any> ReactiveAsset<T>.minusAssign(listener: (value: T, lastValue: T) -> Unit) {
	removeListener(listener)
}
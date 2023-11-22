package com.greentree.engine.moon.assets.provider

data class LastValue(val value: Any) : ValueContext.Element {

	companion object Key : ValueContext.Key<LastValue>

	override val key
		get() = Key
}

@Suppress("UNCHECKED_CAST")
fun <T> ValueContext.lastValue() =
	get(LastValue)?.value as T?

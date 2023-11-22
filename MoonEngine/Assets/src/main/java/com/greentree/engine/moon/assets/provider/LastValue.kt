package com.greentree.engine.moon.assets.provider

data class LastValue(val value: Any) : AssetContext.Element {

	companion object Key : AssetContext.Key<LastValue>

	override val key
		get() = Key
}

@Suppress("UNCHECKED_CAST")
fun <T> AssetContext.lastValue() =
	get(LastValue)?.value as T?

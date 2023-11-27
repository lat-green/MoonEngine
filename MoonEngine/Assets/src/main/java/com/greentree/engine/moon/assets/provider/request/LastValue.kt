package com.greentree.engine.moon.assets.provider.request

data class LastValue(val value: Any) : AssetRequest.Element {

	companion object Key : AssetRequest.Key<LastValue>

	override val key
		get() = Key
}

@Suppress("UNCHECKED_CAST")
fun <T> AssetRequest.lastValue() =
	get(LastValue)?.value as T?

package com.greentree.engine.moon.assets.provider.request

interface AssetRequest {

	operator fun <E : Element> get(key: Key<E>): E?

	interface Key<E : Element> {

		val default: E?
			get() = null
	}

	interface Element : AssetRequest {

		val key: Key<*>

		override fun <R> fold(initial: R, operation: (R, Element) -> R) =
			operation(initial, this)

		@Suppress("UNCHECKED_CAST")
		override fun <E : Element> get(key: Key<E>) = if(key == this.key) this as E else key.default

		override fun minusKey(key: Key<*>) =
			if(key == this.key)
				EmptyAssetRequest
			else
				this
	}

	fun <R> fold(initial: R, operation: (R, Element) -> R): R

	operator fun plus(context: AssetRequest): AssetRequest =
		context.fold(this) { acc, element ->
			CombinedAssetRequest(acc, element)
		}

	fun minusKey(key: Key<*>): AssetRequest
}

operator fun AssetRequest.contains(key: AssetRequest.Key<*>): Boolean = get(key) != key.default

class CombinedAssetRequest(
	private val left: AssetRequest,
	private val element: AssetRequest.Element,
) : AssetRequest {

	override fun <E : AssetRequest.Element> get(key: AssetRequest.Key<E>) = element[key] ?: left[key]

	override fun <R> fold(initial: R, operation: (R, AssetRequest.Element) -> R) =
		left.fold(operation(initial, element), operation)

	override fun minusKey(key: AssetRequest.Key<*>) = left.minusKey(key) + element.minusKey(key)
}

object EmptyAssetRequest : AssetRequest {

	override fun <E : AssetRequest.Element> get(key: AssetRequest.Key<E>) = key.default

	override fun <R> fold(initial: R, operation: (R, AssetRequest.Element) -> R) = initial

	override fun minusKey(key: AssetRequest.Key<*>) = this

	override fun plus(context: AssetRequest) = context
}

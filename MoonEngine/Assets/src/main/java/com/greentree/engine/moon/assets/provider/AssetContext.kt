package com.greentree.engine.moon.assets.provider

interface AssetContext {

	operator fun <E : Element> get(key: Key<E>): E?

	interface Key<E : Element>
	interface Element : AssetContext {

		val key: Key<*>

		override fun <R> fold(initial: R, operation: (R, Element) -> R) =
			operation(initial, this)

		@Suppress("UNCHECKED_CAST")
		override fun <E : Element> get(key: Key<E>) = if(key == this.key) this as E else null

		override fun minusKey(key: Key<*>) =
			if(key == this.key)
				EmptyContext
			else
				this
	}

	fun <R> fold(initial: R, operation: (R, Element) -> R): R

	operator fun plus(context: AssetContext): AssetContext =
		context.fold(this) { acc, element ->
			CombinedContext(acc, element)
		}

	fun minusKey(key: Key<*>): AssetContext
}

operator fun AssetContext.contains(key: AssetContext.Key<*>): Boolean = get(key) != null

class CombinedContext(
	private val left: AssetContext,
	private val element: AssetContext.Element,
) : AssetContext {

	override fun <E : AssetContext.Element> get(key: AssetContext.Key<E>) = element[key] ?: left[key]

	override fun <R> fold(initial: R, operation: (R, AssetContext.Element) -> R) =
		left.fold(operation(initial, element), operation)

	override fun minusKey(key: AssetContext.Key<*>) = left.minusKey(key) + element.minusKey(key)
}

object EmptyContext : AssetContext {

	override fun <E : AssetContext.Element> get(key: AssetContext.Key<E>) = null

	override fun <R> fold(initial: R, operation: (R, AssetContext.Element) -> R) = initial

	override fun minusKey(key: AssetContext.Key<*>) = this

	override fun plus(context: AssetContext) = context
}

package com.greentree.engine.moon.assets.provider

interface ValueContext {

	operator fun <E : Element> get(key: Key<E>): E?

	interface Key<E : Element>
	interface Element : ValueContext {

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

	operator fun plus(context: ValueContext): ValueContext =
		context.fold(this) { acc, element ->
			val removed = acc.minusKey(element.key)
			if(removed === EmptyContext)
				element
			else
				CombinedContext(acc, element)
		}

	fun minusKey(key: Key<*>): ValueContext
}

operator fun ValueContext.contains(key: ValueContext.Key<*>): Boolean = get(key) != null

class CombinedContext(
	val left: ValueContext,
	val element: ValueContext.Element,
) : ValueContext {

	override fun <E : ValueContext.Element> get(key: ValueContext.Key<E>) = left[key] ?: element[key]

	override fun <R> fold(initial: R, operation: (R, ValueContext.Element) -> R) =
		operation(left.fold(initial, operation), element)

	override fun minusKey(key: ValueContext.Key<*>) = left.minusKey(key) + element.minusKey(key)
}

object EmptyContext : ValueContext {

	override fun <E : ValueContext.Element> get(key: ValueContext.Key<E>) = null

	override fun <R> fold(initial: R, operation: (R, ValueContext.Element) -> R) = initial

	override fun minusKey(key: ValueContext.Key<*>) = this

	override fun plus(context: ValueContext) = context
}

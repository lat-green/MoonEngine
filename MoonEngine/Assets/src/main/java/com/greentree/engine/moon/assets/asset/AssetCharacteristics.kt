package com.greentree.engine.moon.assets.asset

interface AssetCharacteristics {

	val lastModified: Long

	fun isValid(): Boolean = true
	fun isConst(): Boolean = false

	fun characteristics(): Characteristics = EmptyCharacteristics
}

interface Characteristics {

	operator fun <E : Element> get(key: Key<E>): E?

	fun <R> fold(initial: R, operation: (R, Element) -> R): R

	operator fun plus(characteristics: Characteristics) = characteristics.fold(this) { acc, element ->
		CombinedCharacteristics(acc, element)
	}

	fun minusKey(key: Key<*>): Characteristics

	interface Key<E : Element>

	interface Element : Characteristics {

		val key: Key<*>

		override fun <R> fold(initial: R, operation: (R, Element) -> R) =
			operation(initial, this)

		override fun <E : Element> get(key: Key<E>) =
			if(key == this.key)
				@Suppress("UNCHECKED_CAST")
				this as E
			else
				null

		override fun minusKey(key: Key<*>) = if(key == this.key) EmptyCharacteristics else this
	}
}

data class CombinedCharacteristics(val left: Characteristics, val element: Characteristics.Element) : Characteristics {

	override fun <E : Characteristics.Element> get(key: Characteristics.Key<E>) =
		left[key] ?: element[key]

	override fun <R> fold(initial: R, operation: (R, Characteristics.Element) -> R) =
		operation(left.fold(initial, operation), element)

	override fun minusKey(key: Characteristics.Key<*>) = left.minusKey(key) + element.minusKey(key)
}

object EmptyCharacteristics : Characteristics {

	override fun <E : Characteristics.Element> get(key: Characteristics.Key<E>) = null

	override fun <R> fold(initial: R, operation: (R, Characteristics.Element) -> R) = initial

	override fun plus(characteristics: Characteristics) = characteristics

	override fun minusKey(key: Characteristics.Key<*>) = this
}

operator fun Characteristics.contains(key: Characteristics.Key<*>) = get(key) != null

infix fun Characteristics.has(key: Characteristics.Key<*>) = key in this
infix fun AssetCharacteristics.has(key: Characteristics.Key<*>) = characteristics().has(key)

data class Const(val state: State = State.CHANGEABLE_FALSE) : Characteristics.Element {

	enum class State(val isConst: Boolean, val isChangeable: Boolean) {
		TRUE(true, true),
		CHANGEABLE_TRUE(true, false),
		CHANGEABLE_FALSE(false, true),
		FALSE(false, false)
	}

	companion object Key : Characteristics.Key<Const>

	override val key
		get() = Key
}

val Const.isConst
	get() = state.isConst
val Characteristics.isConst
	get() = get(Const)?.isConst ?: false
val AssetCharacteristics.isConst
	get() = characteristics().isConst

data class Valid(val state: State = State.CHANGEABLE_TRUE) : Characteristics.Element {

	enum class State(val isValid: Boolean, val isChangeable: Boolean) {
		TRUE(true, true),
		CHANGEABLE_TRUE(true, false),
		CHANGEABLE_FALSE(false, true),
		FALSE(false, false)
	}

	companion object Key : Characteristics.Key<Valid>

	override val key
		get() = Key
}

val Valid.isValid
	get() = state.isValid
val Characteristics.isValid
	get() = get(Valid)?.isValid ?: true
val AssetCharacteristics.isValid
	get() = characteristics().isValid

fun AssetCharacteristics.isChange(lastRead: Long) = isChange(lastModified, lastRead)
fun isChange(lastModified: Long, lastRead: Long) = lastRead < lastModified

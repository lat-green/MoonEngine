package com.greentree.engine.moon.assets.reactive

class FlattenReactiveAsset<T : Any>(private val source: ReactiveAsset<ReactiveAsset<T>>) : ReactiveAsset<T> {

	private val action = mutableListOf<(value: T, lastValue: T) -> Unit>()
	private val l1 = object : (T, T) -> Unit {
		override fun invoke(value: T, lastValue: T) {
			action.event(value, lastValue)
		}
	}
	private val l2 = object : (ReactiveAsset<T>, ReactiveAsset<T>) -> Unit {
		override fun invoke(value: ReactiveAsset<T>, lastValue: ReactiveAsset<T>) {
			lastValue -= l1
			value += l1
			action.event(value.value, lastValue.value)
		}
	}

	init {
		source.value += l1
		source += l2
	}

	override val value: T
		get() = source.value.value

	override fun removeListener(listener: (value: T, lastValue: T) -> Unit) {
		action.remove(listener)
	}

	override fun addListener(listener: (value: T, lastValue: T) -> Unit) {
		action.add(listener)
	}
}

private fun <T : Any> Iterable<(value: T, lastValue: T) -> Unit>.event(
	value: T,
	lastValue: T
) = forEach { it(value, lastValue) }
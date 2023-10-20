package com.greentree.engine.moon.assets.reactive

class MutableReactiveAsset<T : Any>(initValue: T) : ReactiveAsset<T> {

	private val action = mutableListOf<(T, T) -> Unit>()
	override var value = initValue
		set(value) {
			if(value != field) {
				val lastValue = field
				field = value
				action.event(value, lastValue)
			}
		}

	override fun removeListener(listener: (T, T) -> Unit) {
		action.remove(listener)
	}

	override fun addListener(listener: (T, T) -> Unit) {
		action.add(listener)
	}
}

private fun <T : Any> Iterable<(T, T) -> Unit>.event(
	value: T,
	lastValue: T
) = forEach { it(value, lastValue) }

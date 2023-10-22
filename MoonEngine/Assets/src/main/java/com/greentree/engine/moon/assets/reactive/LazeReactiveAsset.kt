package com.greentree.engine.moon.assets.reactive

class LazeReactiveAsset<T : Any> private constructor(private val origin: ReactiveAsset<T>) : ReactiveAsset<T> {

	private val asset = MutableReactiveAsset(origin.value)
	private var isUpdate = false
	private val listener: (T, T) -> Unit = { _, _ ->
		isUpdate = true
	}

	init {
		origin += listener
	}

	override val value: T
		get() {
			if(isUpdate)
				asset.value = origin.value
			return asset.value
		}

	override fun removeListener(listener: (value: T, lastValue: T) -> Unit) = asset.removeListener(listener)

	override fun addListener(listener: (value: T, lastValue: T) -> Unit) = asset.addListener(listener)

	companion object {

		fun <T : Any> ReactiveAsset<T>.toLazy() = when(this) {
			is LazeReactiveAsset -> this
			else -> LazeReactiveAsset(this)
		}
	}
}

fun <T : Any> LazeReactiveAsset<T>.toLazy() = this
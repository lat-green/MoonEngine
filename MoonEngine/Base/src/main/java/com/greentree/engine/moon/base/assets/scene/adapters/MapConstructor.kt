package com.greentree.engine.moon.base.assets.scene.adapters

data class MapConstructor<T : Any, R : Any>(
	val origin: Constructor<T>,
	val block: (T) -> R,
) : Constructor<R> {

	private val value by lazy { block(origin.value()) }

	override fun close() {
		origin.close()
	}

	override fun value() = value
}

fun <T : Any, R : Any> Constructor<T>.map(block: (T) -> R) = MapConstructor(this, block)

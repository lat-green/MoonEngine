package com.greentree.engine.moon.base.assets.scene.adapters

fun interface Constructor<T : Any> : AutoCloseable {

	override fun close() {
	}

	fun value(): T
}

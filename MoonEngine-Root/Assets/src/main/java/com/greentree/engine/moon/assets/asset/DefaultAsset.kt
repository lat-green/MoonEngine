package com.greentree.engine.moon.assets.asset

class DefaultAsset<T : Any> private constructor(private val sources: Iterable<Asset<T>>) : Asset<T> {

	companion object {

		fun <T : Any> newAsset(sources: Iterable<Asset<T>>): Asset<T> {
			return DefaultAsset(sources)
		}
	}

	override fun isValid() = sources.any { it.isValid }

	override fun toString(): String {
		var sources = sources.toString()
		sources = sources.substring(1, sources.length - 1)
		return "Default(${sources})"
	}

	override val value: T
		get() {
			println(sources)
			return sources.first { it.isValid }.value
		}
	override val lastModified
		get() = sources.filter { it.isValid }.maxOf { it.lastModified }
}
package com.greentree.engine.moon.assets.asset

class DefaultAsset<T : Any> private constructor(private val sources: Iterable<Asset<T>>) : Asset<T> {

	companion object {

		fun <T : Any> newAsset(sources: Iterable<Asset<T>>): Asset<T> {
			return DefaultAsset(sources)
		}
	}

	override val value
		get() = sources.first { it.isValid }.value
	override val lastModified
		get() = sources.filter { it.isValid }.maxOf { it.lastModified }
}
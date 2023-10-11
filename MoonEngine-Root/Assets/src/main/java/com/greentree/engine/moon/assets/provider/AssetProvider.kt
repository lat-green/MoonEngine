package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.serializator.loader.AssetLoader

interface AssetProvider<T : Any> {

	fun load(context: AssetLoader.Context): T

	fun openConnection(): Connection<T>

	interface Connection<T : Any> {

		val isChange: Boolean
	}
}
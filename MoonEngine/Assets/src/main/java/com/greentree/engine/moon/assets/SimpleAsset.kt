package com.greentree.engine.moon.assets

data class SimpleAsset<T : Any>(
	override val value: T,
) : Asset<T>
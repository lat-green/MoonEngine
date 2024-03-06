package com.greentree.engine.moon.assets.asset

data class SimpleAsset<T : Any>(
	override val value: T,
) : Asset<T>
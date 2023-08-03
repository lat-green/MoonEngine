package com.greentree.engine.moon.assets.asset

data class ConstAsset<T : Any>(override val value: T) : Asset<T> {

	companion object {

		fun <T : Any> newAsset(value: T?): Asset<T> {
			return when(value) {
				null -> NotValidAsset.instance()
				else -> ConstAsset(value)
			}
		}
	}

	override val lastModified: Long
		get() = 0

	override fun isConst() = true

	override fun toString(): String {
		return "Const[$value]"
	}
}
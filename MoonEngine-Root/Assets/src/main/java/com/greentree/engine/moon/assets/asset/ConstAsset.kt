package com.greentree.engine.moon.assets.asset

data class ConstAsset<T : Any>(override val value: T) : Asset<T> {

	companion object {

		fun <T : Any> newAsset(value: T?): Asset<T> = ConstAsset(value!!)
	}

	init {
		println(value)
	}

	override val lastModified: Long
		get() = 0L

	override fun isConst() = true

	override fun toString(): String {
		return "Const[$value]"
	}
}
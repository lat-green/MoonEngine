package com.greentree.engine.moon.assets.asset

import com.greentree.engine.moon.assets.Value1Function

data class ConstAsset<T : Any>(override val value: T) : Asset<T> {

	companion object {

		fun <T : Any> newAsset(value: T?): Asset<T> = ConstAsset(value!!)
	}

	override val lastModified: Long
		get() = 0L

	override fun isConst() = true

	override fun toString(): String {
		return "Const[$value]"
	}

	override fun <R : Any> map(function: Value1Function<T, R>): Asset<R> {
		return ConstAsset(function(value))
	}
}
package com.greentree.engine.moon.assets.asset

data class ConstAsset<T : Any>(override val value: T) : Asset<T> {

	companion object {

		fun <T : Any> newAsset(value: T?): Asset<T> {
			return when(value) {
				null -> ThrowAsset(NullPointerException("create ConstAsset(value=null)"))
				else -> ConstAsset(value)
			}
		}
	}

	override fun hasCharacteristic(characteristic: AssetCharacteristic): Boolean {
		return when(characteristic) {
			AssetCharacteristic.EVER_VALID -> true
			else -> super.hasCharacteristic(characteristic)
		}
	}

	override val lastModified: Long
		get() = Long.MIN_VALUE

	override fun isConst() = true

	override fun toString(): String {
		return "Const[$value]"
	}
}
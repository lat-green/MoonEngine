package com.greentree.engine.moon.assets.asset

data class ThrowAsset<T : Any>(val exception: Exception) : Asset<T> {

	override fun isValid() = false
	override fun isConst() = true

	override fun toString(): String {
		return "ThrowAsset[$exception]"
	}

	override fun hasCharacteristic(characteristic: AssetCharacteristic): Boolean {
		return when(characteristic) {
			AssetCharacteristic.NEVER_VALID -> true
			else -> super.hasCharacteristic(characteristic)
		}
	}

	override val value: T
		get() = throw RuntimeException(exception)
	override val lastModified: Long
		get() = Long.MIN_VALUE
}
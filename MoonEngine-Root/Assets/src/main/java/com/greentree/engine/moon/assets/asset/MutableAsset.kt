package com.greentree.engine.moon.assets.asset

class MutableAsset<T : Any>(initValue: T) : Asset<T> {

	override var lastModified = System.currentTimeMillis()
		private set
	override var value: T = initValue
		set(value) {
			if(field != value) {
				lastModified = System.currentTimeMillis()
				field = value
			}
		}

	override fun isCache() = true
}
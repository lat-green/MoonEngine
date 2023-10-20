package com.greentree.engine.moon.assets.provider

class MutableAssetProvider<T : Any>(initValue: T) : AssetProvider<T> {

	override var lastModified = System.currentTimeMillis()
		private set

	override var value: T = initValue
		set(value) {
			if(field != value) {
				lastModified = System.currentTimeMillis()
				field = value
			}
		}

	override fun value(ctx: AssetProvider.Context) = value
}

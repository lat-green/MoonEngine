package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.context.AssetContext

class MutableAssetProvider<T : Any>(value: T) : AssetProvider<T> {

	override var lastModified = System.currentTimeMillis()
		private set
	override var value: T = value
		set(value) {
			if(field != value) {
				val lastModified = System.currentTimeMillis()
				if(lastModified == this.lastModified)
					this.lastModified++
				else
					this.lastModified = lastModified
				field = value
			}
		}

	override fun value(ctx: AssetContext) = value
}

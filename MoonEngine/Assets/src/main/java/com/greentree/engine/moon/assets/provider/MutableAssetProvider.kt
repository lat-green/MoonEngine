package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.provider.request.AssetRequest

class MutableAssetProvider<T : Any>(value: T) : AssetProvider<T>, ChangeHandler {

	override var lastModified = System.currentTimeMillis()
		private set
	var value: T = value
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
	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequenceOf(this)

	override fun value(ctx: AssetRequest) = value
}

package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.response.AssetResponse
import com.greentree.engine.moon.assets.provider.response.ConstResult
import java.lang.reflect.Array.*

class MutableAssetProvider<T : Any>(value: T) : AssetProvider<T> {

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

	override fun value(ctx: AssetRequest) = ConstResult(value)
}

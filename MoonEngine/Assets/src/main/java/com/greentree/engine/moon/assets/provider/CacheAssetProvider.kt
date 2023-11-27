package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.LastValue
import com.greentree.engine.moon.assets.provider.request.TryNotUpdate
import com.greentree.engine.moon.assets.provider.request.contains
import com.greentree.engine.moon.assets.provider.response.AssetResponse

class CacheAssetProvider<T : Any>(
	private val origin: AssetProvider<T>,
) : AssetProvider<T> {

	private var lastUpdate = 0L
	private var needUpdate = false
	private lateinit var cache: AssetResponse<T>

	override fun value(ctx: AssetRequest): AssetResponse<T> {
		updateLastModified()
		if(!::cache.isInitialized || (needUpdate && TryNotUpdate !in ctx)) {
			needUpdate = false
			var ctx = ctx
			ctx = ctx.minusKey(LastValue)
			if(::cache.isInitialized)
				ctx += LastValue(cache)
			val cache = origin.value(ctx)
			this.cache = cache
			return cache
		}
		return cache
	}

	private fun updateLastModified() {
		val lastUpdate = origin.lastModified
		if(lastUpdate > this.lastUpdate) {
			this.lastUpdate = lastUpdate
			needUpdate = true
		}
	}

	override val lastModified: Long
		get() {
			updateLastModified()
			return lastUpdate
		}
}

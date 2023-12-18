package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.LastValue
import com.greentree.engine.moon.assets.provider.request.TryNotUpdate
import com.greentree.engine.moon.assets.provider.request.contains
import com.greentree.engine.moon.assets.provider.response.AssetResponse

class CacheAssetProvider<T : Any>(
	private val origin: AssetProvider<T>,
) : AssetProvider<T>, AssetProviderCharacteristics by origin {

	private var lastUpdate = 0L
	private lateinit var cache: AssetResponse<T>

	override fun value(ctx: AssetRequest): AssetResponse<T> {
		val lastUpdate = origin.lastModified
		if(!::cache.isInitialized || (lastUpdate > this.lastUpdate && TryNotUpdate !in ctx)) {
			this.lastUpdate = lastUpdate
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
}

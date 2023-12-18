package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.LastValue
import com.greentree.engine.moon.assets.provider.request.TryNotUpdate
import com.greentree.engine.moon.assets.provider.request.contains

class CacheAssetProvider<T : Any>(
	private val origin: AssetProvider<T>,
) : AssetProvider<T>, AssetProviderCharacteristics by origin {

	private var lastUpdate = 0L
	private lateinit var cache: AssetResponse<T>

	override fun value(ctx: AssetRequest): T {
		val lastUpdate = origin.lastModified
		if(!::cache.isInitialized || (lastUpdate > this.lastUpdate && TryNotUpdate !in ctx)) {
			this.lastUpdate = lastUpdate
			var ctx = ctx
			ctx = ctx.minusKey(LastValue)
			if(::cache.isInitialized)
				ctx += LastValue(cache)
			val cache = AssetResponse { origin.value(ctx) }
			this.cache = cache
			return cache.getOrThrow()
		}
		return cache.getOrThrow()
	}
}

private fun <T: Any> AssetResponse<T>.getOrThrow() = when(this) {
	is ResultAssetResponse -> value
	is ExceptionAssetResponse -> throw exception
}

private sealed interface AssetResponse<T : Any>

private fun <T : Any> AssetResponse(block: () -> T): AssetResponse<T> = try {
	ResultAssetResponse(block())
} catch(e: Exception) {
	ExceptionAssetResponse(e)
}

private data class ResultAssetResponse<T : Any>(val value: T) : AssetResponse<T>
private data class ExceptionAssetResponse<T : Any>(val exception: Exception) : AssetResponse<T>
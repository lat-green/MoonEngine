package com.greentree.engine.moon.assets.provider

class CacheAssetProvider<T : Any>(
	private val origin: AssetProvider<T>,
) : AssetProvider<T> by origin {

	private var cache: T? = null
	private var lastUpdate = 0L
	private var needUpdate = false
	override val value: T
		get() = cache ?: super.value

	override fun value(ctx: AssetContext): T {
		var ctx = ctx
		if(needUpdate) {
			val cache = origin.value(ctx)
			this.cache = cache
			return cache
		}
		val lastUpdate = origin.lastModified
		if(lastUpdate > this.lastUpdate) {
			this.lastUpdate = lastUpdate
			ctx = ctx.minusKey(LastValue)
			cache?.let {
				ctx += LastValue(it)
			}
			val cache = origin.value(ctx)
			this.cache = cache
			return cache
		}
		return cache!!
	}

	override val lastModified: Long
		get() {
			val lastUpdate = origin.lastModified
			if(lastUpdate > this.lastUpdate) {
				this.lastUpdate = lastUpdate
				needUpdate = true
			}
			return lastUpdate
		}
}

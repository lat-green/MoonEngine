package com.greentree.engine.moon.assets.asset

class CacheAsset<T : Any> private constructor(private val asset: Asset<T>) : Asset<T> {

	companion object {

		fun <T : Any> newAsset(asset: Asset<T>): Asset<T> {
			if(asset.isConst() || asset.isCache())
				return asset
			return CacheAsset(asset)
		}
	}

	override var lastModified = asset.lastModified
		private set
	private var cache = asset.value
	override val value: T
		get() {
			if(lastModified < asset.lastModified) {
				lastModified = asset.lastModified
				cache = asset.value
			}
			return cache
		}

	override fun isConst() = asset.isConst()
	override fun isCache() = true
}
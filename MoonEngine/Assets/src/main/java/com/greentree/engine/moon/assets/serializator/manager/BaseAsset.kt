package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.engine.moon.assets.Asset
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.lastModified

data class BaseAsset<T : Any>(
	val provider: AssetProvider<T>,
) : Asset<T> {

	private var lastUpdate = provider.lastModified
	override var value = provider.value()
		private set
		get() {
			val lastModified = provider.lastModified
			if(lastModified > lastUpdate) {
				lastUpdate = lastModified
				field = provider.value()
			}
			return field
		}
}

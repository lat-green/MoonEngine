package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader

class ComponentAsset<T : Any>(
	val context: AssetLoader.Context,
	val block: (AssetComponentLoader.Context) -> T,
) : Asset<T>, AssetComponentLoader.Context {

	init {
		value
	}

	private var sources = mutableListOf<Asset<*>>()
	override val value: T
		get() {
			sources.clear()
			return block(this)
		}
	override val lastModified: Long
		get() = sources.maxOf { it.lastModified }

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): T {
		val source = context.load(type, key)
		sources.add(source)
		return source.value
	}
}

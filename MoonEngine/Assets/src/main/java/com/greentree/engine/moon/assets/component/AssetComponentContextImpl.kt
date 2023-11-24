package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo

class AssetComponentContextImpl : AssetComponentContext {

	val loaders = mutableMapOf<TypeInfo<*>, AssetComponentLoader<*, *>>()

	override fun <T, K : AssetComponentKey<T>> loadFunction(key: TypeInfo<K>): (K) -> AssetComponentProvider<T> {
		val loader = loaders[key] as AssetComponentLoader<K, T>
		return { key -> loader.load(this, key) }
	}
}
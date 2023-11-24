package com.greentree.engine.moon.assets.component

interface AssetComponentLoader<K : AssetComponentKey<T>, T> {

	fun load(ctx: AssetComponentContext, key: K): AssetComponentProvider<T>
}

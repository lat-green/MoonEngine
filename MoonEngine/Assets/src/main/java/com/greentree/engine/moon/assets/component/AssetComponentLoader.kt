package com.greentree.engine.moon.assets.component

interface AssetComponentLoader<K : AssetComponentKey<*>> {

	fun load(ctx: AssetComponentContext, key: K): Any?
}
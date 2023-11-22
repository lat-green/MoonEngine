package com.greentree.engine.moon.assets.component

interface AssetComponentContext {

	fun <T> load(key: AssetComponentKey<T>): T
}

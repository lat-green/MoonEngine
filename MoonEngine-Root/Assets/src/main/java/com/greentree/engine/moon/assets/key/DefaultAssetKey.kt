package com.greentree.engine.moon.assets.key

data class DefaultAssetKey(val keys: Iterable<AssetKey>) : AssetKey {

	constructor(vararg keys: AssetKey) : this(keys.asIterable())
}
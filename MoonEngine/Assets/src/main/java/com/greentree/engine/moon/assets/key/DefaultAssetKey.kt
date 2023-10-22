package com.greentree.engine.moon.assets.key

import com.greentree.commons.util.iterator.IteratorUtil

data class DefaultAssetKey(val keys: Iterable<AssetKey>) : AssetKey {

	constructor(vararg keys: AssetKey) : this(keys.asIterable())

	override fun toString(): String {
		return "DefaultAssetKey(keys=${IteratorUtil.toString(keys)})"
	}
}
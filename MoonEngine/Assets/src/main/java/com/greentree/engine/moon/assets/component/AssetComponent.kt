package com.greentree.engine.moon.assets.component

import java.io.Serializable

interface AssetComponent<T> : Serializable {

	val value: T
	val key: AssetComponentKey<T>
}

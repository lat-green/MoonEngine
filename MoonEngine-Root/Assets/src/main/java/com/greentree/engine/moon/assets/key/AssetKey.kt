package com.greentree.engine.moon.assets.key

import java.io.Serializable

interface AssetKey : Serializable {

	fun type(): AssetKeyType {
		return AssetKeyType.DEFAULT
	}
}
package com.greentree.engine.moon.assets.location

import com.greentree.engine.moon.assets.key.AssetKey
import java.util.*

interface AssetLocation {

	fun getKey(name: String): AssetKey = getKeyOptional(name).get()

	fun getKeyOptional(name: String): Optional<AssetKey>

	fun isExist(name: String) = getKeyOptional(name).isPresent
}
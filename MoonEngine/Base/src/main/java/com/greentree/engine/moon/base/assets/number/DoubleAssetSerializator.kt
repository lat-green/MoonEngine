package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

object DoubleAssetSerializator : AssetSerializator<Double> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Double {
		val str = manager.load<String>(key)
		return str.toDouble()
	}
}

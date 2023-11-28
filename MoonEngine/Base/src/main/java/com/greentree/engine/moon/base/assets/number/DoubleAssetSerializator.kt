package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object DoubleAssetSerializator : AssetSerializator<Double> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<Double> {
		val str = manager.load<String>(key)
		return str.map(StringToDouble)
	}

	private object StringToDouble : Value1Function<String, Double> {

		override fun apply(value: String): Double {
			return value.toDouble()
		}
	}
}

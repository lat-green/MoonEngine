package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object BooleanAssetSerializator : AssetSerializator<Boolean> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): Asset<Boolean> {
		val str = manager.load<String>(ckey)
		return str.map(StringToBoolean())
	}

	private class StringToBoolean : Value1Function<String, Boolean> {

		override fun apply(str: String): Boolean {
			return when(str) {
				"1", "true", "True", "T" -> true
				"0", "false", "False", "F" -> false
				else -> throw IllegalArgumentException("Unexpected value: $str")
			}
		}
	}
}

package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

object BooleanAssetSerializator : AssetSerializator<Boolean> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): Boolean {
		return when(val str = manager.load<String>(ckey)) {
			"1", "true", "True", "T" -> true
			"0", "false", "False", "F" -> false
			else -> throw IllegalArgumentException("Unexpected value: $str")
		}
	}
}

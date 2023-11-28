package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object IntAssetSerializator : AssetSerializator<Int> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<Int> {
		val str = manager.load<String>(key)
		return str.map(StringToInteger)
	}

	private object StringToInteger : Value1Function<String, Int> {

		override fun apply(str: String): Int {
			return str.toInt()
		}
	}
}

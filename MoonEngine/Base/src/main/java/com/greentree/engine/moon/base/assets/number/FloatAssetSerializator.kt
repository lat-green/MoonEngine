package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object FloatAssetSerializator : AssetSerializator<Float> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<Float> {
		val str = manager.load<String>(key)
		return str.map(StringToFloat)
	}

	private object StringToFloat : Value1Function<String, Float> {

		override fun apply(value: String): Float {
			return value.toFloat()
		}
	}
}

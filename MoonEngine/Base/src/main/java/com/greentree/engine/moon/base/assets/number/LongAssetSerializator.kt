package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object LongAssetSerializator : AssetSerializator<Long> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<Long> {
		val str = manager.load<String>(key)
		return str.map(StringToLong)
	}

	private object StringToLong : Value1Function<String, Long> {

		override fun apply(str: String): Long {
			return str.toLong()
		}
	}
}

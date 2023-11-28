package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object ByteAssetSerializator : AssetSerializator<Byte> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<Byte> {
		val str = manager.load<String>(key)
		return str.map(StringToByte)
	}

	private object StringToByte : Value1Function<String, Byte> {

		override fun apply(str: String): Byte {
			return str.toByte()
		}
	}
}

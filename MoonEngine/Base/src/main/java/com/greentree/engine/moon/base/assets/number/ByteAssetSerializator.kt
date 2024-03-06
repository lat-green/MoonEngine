package com.greentree.engine.moon.base.assets.number

import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

object ByteAssetSerializator : AssetSerializator<Byte> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Byte {
		val str = manager.load<String>(key)
		return str.toByte()
	}
}

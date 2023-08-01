package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.DefaultAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.DefaultAssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager

class DefaultSerializator<T : Any>(type: TypeInfo<T>) : TypedAssetSerializator<T>(type) {

	override fun canLoad(manager: AssetManager, key: AssetKey): Boolean {
		return key is DefaultAssetKey
	}

	override fun load(manager: AssetManager, key: AssetKey): Asset<T> {
		if(key is DefaultAssetKey) {
			val values = key.keys.map { manager.load(type, it) }
			return DefaultAsset.newAsset(values)
		}
		throw IllegalArgumentException()
	}
}
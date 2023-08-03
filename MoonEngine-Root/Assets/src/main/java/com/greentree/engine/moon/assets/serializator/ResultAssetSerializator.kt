package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ConstAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.marker.NotMyKeyType

class ResultAssetSerializator<T : Any>(type: TypeInfo<T>) : TypedAssetSerializator<T>(type) {

	override fun load(context: AssetManager, key: AssetKey): Asset<T>? {
		if(key is ResultAssetKey) {
			val result = key.result
			if(type.isInstance(result))
				return ConstAsset(result as T)
			return null
		}
		throw NotMyKeyType
	}
}
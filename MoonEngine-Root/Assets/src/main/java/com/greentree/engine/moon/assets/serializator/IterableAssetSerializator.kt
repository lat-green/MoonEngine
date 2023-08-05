package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.MIAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.IterableAssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.marker.NotMyKeyType

class IterableAssetSerializator<T : Any> : AbstractIterableAssetSerializator<T> {
	constructor(cls: Class<T>) : super(cls)
	constructor(type: TypeInfo<T>) : super(type)

	override fun load(manager: AssetManager, key: AssetKey): Asset<Iterable<T>> {
		if(key is IterableAssetKey) {
			val iter = key.map { manager.load(ITER_TYPE, it) }
			return MIAsset(iter)
		}
		throw NotMyKeyType
	}
}
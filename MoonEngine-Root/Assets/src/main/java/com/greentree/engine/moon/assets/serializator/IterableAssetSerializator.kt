package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.util.iterator.IteratorUtil
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.MIAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.IterableAssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager

class IterableAssetSerializator<T> : AbstractIterableAssetSerializator<T> {
	constructor(cls: Class<T>) : super(cls)
	constructor(type: TypeInfo<T>) : super(type)

	override fun canLoad(manager: AssetManager, key: AssetKey): Boolean {
		if(key is IterableAssetKey) {
			for(s in key)
				if(!manager.canLoad(ITER_TYPE, s))
					return false
			return true
		}
		return false
	}

	override fun load(manager: AssetManager, key: AssetKey): Asset<Iterable<T>> {
		if(key is IterableAssetKey) {
			val iter =
				IteratorUtil.clone(IteratorUtil.map(key) { k -> manager.load(ITER_TYPE, k) })
			return MIAsset(iter)
		}
		throw IllegalArgumentException()
	}
}
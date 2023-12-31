package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.IterableAssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.MIAssetProvider
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader

class IterableAssetSerializator<T : Any> : AbstractIterableAssetSerializator<T> {
	constructor(cls: Class<T>) : super(cls)
	constructor(type: TypeInfo<T>) : super(type)

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<Iterable<T>> {
		if(key is IterableAssetKey) {
			val iter = key.map { manager.load(ITER_TYPE, it) }
			return MIAssetProvider(iter)
		}
		throw NotSupportedKeyType
	}
}
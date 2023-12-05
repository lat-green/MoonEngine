package com.greentree.engine.moon.assets.react.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyAndType
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.NotSupportedType
import com.greentree.engine.moon.assets.key.AssetKey

class MultiReactAssetLoader(val loaders: Iterable<ReactAssetLoader>) : ReactAssetLoader {

	override suspend fun <T : Any> load(context: ReactAssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
		for(loader in loaders) {
			try {
				return loader.load(context, type, key)
			} catch(_: NotSupportedType) {
			} catch(_: NotSupportedKeyType) {
			} catch(_: NotSupportedKeyAndType) {
			}
		}
		throw NotSupportedKeyAndType
	}
}

package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.DefaultAssetKey

data object DefaultAssetLoader : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
		if(key is DefaultAssetKey) {
			val a = key.keys.map {
				runCatching {
					context.loadAsset(type, it)
				}
			}
			var b = a.reduce { a, b ->
				when {
					a.isFailure -> b
					b.isFailure -> a
					else -> {
						a
					}
				}
			}.getOrNull()!!

			return b.value
		}
		throw NotSupportedKeyType
	}
}
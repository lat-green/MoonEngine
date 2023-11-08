package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider

interface AssetProviderLoader {

	fun <T : Any> load(context: Context, type: TypeInfo<T>, key: AssetKey): AssetProvider<T>?

	interface Context {

		fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): AssetProvider<T>
	}
}
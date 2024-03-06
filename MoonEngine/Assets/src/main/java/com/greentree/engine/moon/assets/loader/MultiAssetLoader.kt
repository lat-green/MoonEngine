package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyAndType
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.NotSupportedType
import com.greentree.engine.moon.assets.key.AssetKey

data class MultiAssetLoader(
	val loaders: Iterable<AssetLoader>,
) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
		val exceptions = mutableListOf<Throwable>()
		for(loader in loaders) {
			try {
				return loader.load(context, type, key)
			} catch(_: NotSupportedType) {
			} catch(_: NotSupportedKeyType) {
			} catch(_: NotSupportedKeyAndType) {
			} catch(e: Throwable) {
				exceptions.add(e)
			}
		}
		if(exceptions.size == 1)
			throw exceptions.first()
		val exception = NoOneLoaderCanNotLoadType(type, key, loaders)
		for(e in exceptions)
			exception.addSuppressed(e)
		throw exception
	}
}
package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map

class SimpleAssetLoaderWrapper<T : Any, R : Any>(private val origin: SimpleAssetProviderLoader<T, R>) :
	AssetProviderLoader {

	private val resultType = origin.resultType
	private val sourceType = origin.sourceType

	override fun <K : Any> load(
		context: AssetProviderLoader.Context,
		type: TypeInfo<K>,
		key: AssetKey
	): AssetProvider<K>? {
		if(TypeUtil.isExtends(type, this.resultType)) {
			val source = context.load(sourceType, key)
			return source.map(origin) as AssetProvider<K>
		}
		return null
	}
}
//fun <T : Any, R : Any> MutableAssetManager.addLoader(loader: SimpleAssetLoader<T, R>) =
//	addLoader(SimpleAssetLoaderWrapper(loader))
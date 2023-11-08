package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.provider.AssetFunction1

interface SimpleAssetProviderLoader<T : Any, R : Any> : AssetFunction1<T, R> {

	val resultType
		get() = TypeUtil.getTtype(
			TypeInfoBuilder.getTypeInfo(javaClass),
			SimpleAssetProviderLoader::class.java
		).typeArguments[1] as TypeInfo<R>
	val sourceType
		get() = TypeUtil.getTtype(
			TypeInfoBuilder.getTypeInfo(javaClass),
			SimpleAssetProviderLoader::class.java
		).typeArguments[0] as TypeInfo<T>
}


package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil

interface AssetComponentLoader<K : AssetComponentKey<T>, T> : AssetFunctionComponentLoader<K, T> {

	override fun loadFunction(ctx: AssetComponentContext): (K) -> AssetComponentProvider<T> {
		return { key -> load(ctx, key) }
	}

	fun load(ctx: AssetComponentContext, key: K): AssetComponentProvider<T>
}

interface AssetFunctionComponentLoader<K : AssetComponentKey<T>, T> {

	val keyType: TypeInfo<K>
		get() = TypeUtil.getTtype(
			javaClass.type,
			AssetFunctionComponentLoader::class.java
		).typeArguments[0] as TypeInfo<K>
	val resultType: TypeInfo<T>
		get() = TypeUtil.getTtype(
			javaClass.type,
			AssetFunctionComponentLoader::class.java
		).typeArguments[1] as TypeInfo<T>

	fun loadFunction(ctx: AssetComponentContext): (K) -> AssetComponentProvider<T>
}

val <T> Class<T>.type get() = TypeInfoBuilder.getTypeInfo(this)!!

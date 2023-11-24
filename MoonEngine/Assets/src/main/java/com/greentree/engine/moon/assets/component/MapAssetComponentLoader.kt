package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil

interface MapAssetComponentLoader<T, R> {

	val sourceType
		get() = TypeUtil.getTtype(javaClass.type, MapAssetComponentLoader::class.java).typeArguments[0] as TypeInfo<T>
	val resultType
		get() = TypeUtil.getTtype(javaClass.type, MapAssetComponentLoader::class.java).typeArguments[1] as TypeInfo<R>

	fun load(source: T): R
}

data class MapLoaderKey<T : Any, R>(
	override val resultType: TypeInfo<R>,
	val source: T,
) : AssetComponentKey<R> {

	private val sourceType
		get() = source::class.type
	override val type: TypeInfo<MapLoaderKey<T, R>>
		get() = TypeInfoBuilder.getTypeInfo(MapLoaderKey::class.java, sourceType, resultType)
}

fun <T, R> AssetComponentContext.load(type: TypeInfo<R>, source: AssetComponentKey<T>) =
	load(MapLoaderKey(type, source))

data class MapAssetComponentLoaderWrapper<T : Any, R>(val origin: MapAssetComponentLoader<T, R>) :
	AssetFunctionComponentLoader<MapLoaderKey<T, R>, R> {

	override val resultType
		get() = origin.resultType

	override fun loadFunction(ctx: AssetComponentContext): (MapLoaderKey<T, R>) -> AssetComponentProvider<R> {
		return { key ->
			ConstAssetComponentProvider(origin.load(key.source))
		}
	}

	override val keyType: TypeInfo<MapLoaderKey<T, R>>
		get() = TypeInfoBuilder.getTypeInfo(MapLoaderKey::class.java, origin.sourceType, origin.resultType)
}
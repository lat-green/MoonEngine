package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.IterableAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

class MapIterableAssetSerializator<T : Any, R : Any>(
	private val T_TYPE: TypeInfo<T>,
	private val R_TYPE: TypeInfo<R>,
) :
	AssetSerializator<Iterable<R>> {

	private val T_ITER_TYPE: TypeInfo<Iterable<T>>
	private val R_ITER_TYPE: TypeInfo<Iterable<R>>

	init {
		T_ITER_TYPE = TypeInfoBuilder.getTypeInfo(Iterable::class.java, T_TYPE)
		R_ITER_TYPE = TypeInfoBuilder.getTypeInfo(Iterable::class.java, R_TYPE)
	}

	override val type: TypeInfo<Iterable<R>>
		get() = R_ITER_TYPE

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<Iterable<R>> {
		val iter = manager.load(T_ITER_TYPE, key)
		return IterableAsset(MapIterableValue(manager, iter, R_TYPE))
	}

	private class MapIterableValue<T : Any, R : Any>(
		val manager: AssetLoader.Context,
		val iter: Asset<Iterable<T>>,
		val R_TYPE: TypeInfo<R>,
	) :
		Asset<Iterable<out Asset<R>>> {

		override val value: Iterable<out Asset<R>>
			get() = iter.value.map { manager.load<R>(R_TYPE, it) }
		override val lastModified: Long
			get() = iter.lastModified

		override fun isConst() = iter.isConst()
	}
}
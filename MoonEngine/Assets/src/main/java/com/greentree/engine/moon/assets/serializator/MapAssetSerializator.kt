package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader

interface MapAssetSerializator<T : Any, R : Any> : AssetSerializator<Iterable<R>> {

	val inputType: TypeInfo<T>
		get() = TypeUtil.getFirstAtgument(javaClass, MapAssetSerializator::class.java)
	private val inputIterableType: TypeInfo<Iterable<T>>
		get() = TypeInfoBuilder.getTypeInfo(Iterable::class.java, inputType)

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<Iterable<R>> {
		val input = manager.load(inputIterableType, key)
		return MapIterableAssetFunction(input)
	}

	class MapIterableAssetFunction<T : Any, R : Any>(
		val source: AssetProvider<Iterable<T>>,
	) : AssetProvider<Iterable<R>> {

		override fun value(ctx: AssetRequest): Iterable<R> {
			TODO("Not yet implemented")
		}

		override val changeHandlers: Sequence<ChangeHandler>
			get() = source.changeHandlers
	}
}
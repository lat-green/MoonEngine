package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil

abstract class AbstractIterableAssetSerializator<T> : AssetSerializator<Iterable<T>> {

	val ITER_TYPE: TypeInfo<T>
	override val type: TypeInfo<Iterable<T>>

	constructor(cls: Class<T>?) : this(TypeInfoBuilder.getTypeInfo<T>(cls))
	constructor(type: TypeInfo<T>) {
		ITER_TYPE = type
		this.type = TypeInfoBuilder.getTypeInfo(Iterable::class.java, ITER_TYPE)
	}

	protected constructor() {
		ITER_TYPE = TypeUtil.getFirstAtgument(
			TypeInfoBuilder.getTypeInfo(javaClass),
			AbstractIterableAssetSerializator::class.java
		)
		type = TypeInfoBuilder.getTypeInfo(Iterable::class.java, ITER_TYPE)
	}
}
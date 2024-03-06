package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.manager.MutableAssetManager

interface SimpleAssetSerializator<T : Any, R : Any> : (T) -> R {

	val resultType
		get() = TypeUtil.getTtype(
			TypeInfoBuilder.getTypeInfo(javaClass),
			SimpleAssetSerializator::class.java
		).typeArguments[1] as TypeInfo<R>
	val sourceType
		get() = TypeUtil.getTtype(
			TypeInfoBuilder.getTypeInfo(javaClass),
			SimpleAssetSerializator::class.java
		).typeArguments[0] as TypeInfo<T>

	override fun invoke(source: T): R
}

fun <T : Any, R : Any> MutableAssetManager.addSerializator(function: SimpleAssetSerializator<T, R>) =
	addSerializator(SimpleAssetSerializatorWrapper(function))


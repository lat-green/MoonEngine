package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.serializator.manager.MutableAssetManager

interface SimpleAssetSerializator<T : Any, R : Any> : Value1Function<T, R> {

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
}

fun <T : Any, R : Any> MutableAssetManager.addSerializator(function: SimpleAssetSerializator<T, R>) =
	addSerializator(SimpleAssetSerializatorWrapper(function))


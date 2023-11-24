package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil
import java.io.Serializable

interface AssetComponentKey<T> : Serializable {

	val type: TypeInfo<out AssetComponentKey<T>>
		get() = TypeInfoBuilder.getTypeInfo(javaClass)
	val resultType: TypeInfo<T>
		get() = TypeUtil.getFirstAtgument(javaClass, AssetComponentKey::class.java)
}

val <T, K : AssetComponentKey<T>> K.keyType
	get() = type as TypeInfo<K>
package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.key.AssetKeyType

interface AssetDefaultInfo<T> {

	val type: TypeInfo<T>

	fun getDefault(context: Context, type: AssetKeyType): T

	interface Context {

		fun <T> getDefault(cls: TypeInfo<T>, type: AssetKeyType): T
		fun <T> getDefault(cls: Class<T>, type: AssetKeyType): T = getDefault(TypeInfoBuilder.getTypeInfo(cls), type)
	}
}
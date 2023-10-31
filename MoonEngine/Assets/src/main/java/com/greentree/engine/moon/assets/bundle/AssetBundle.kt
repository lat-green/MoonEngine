package com.greentree.engine.moon.assets.bundle

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeInfoBuilder.*

interface AssetBundle {
	fun <T: Any> load(type: TypeInfo<T>, name: String): T
	fun has(name: String): Boolean
}

inline fun <reified T: Any> AssetBundle.load(name: String): T = load(getTypeInfo(T::class.java), name)
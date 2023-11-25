package com.greentree.engine.moon.assets.provider.context

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.key.AssetKey

interface AssetContextManager : AssetContext.Element {
	companion object Key : AssetContext.Key<AssetContextManager>

	fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): T
	fun <T : Any> load(type: Class<T>, key: AssetKey) =
		load(getTypeInfo(type) as TypeInfo<T>, key)
}

val AssetContext.manager
	get() = get(AssetContextManager)!!

inline fun <reified T : Any> AssetContextManager.load(key: AssetKey) = load(T::class.java, key)
inline fun <reified T : Any> AssetContext.load(key: AssetKey) = manager.load(T::class.java, key)
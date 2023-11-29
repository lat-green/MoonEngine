package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import kotlin.reflect.KClass

interface AssetComponentLoader {

	fun <T: Any> load(ctx: Context, type: TypeInfo<T>, key: AssetKey): T

	interface Context {

		fun <T: Any> load(type: TypeInfo<T>, key: AssetKey): T
	}
}

fun <T: Any> AssetComponentLoader.Context.load(type: TypeInfo<T>, key: String) = load(type, ResourceAssetKey(key))
fun <T: Any> AssetComponentLoader.Context.load(type: TypeInfo<T>, key: Any) = load(type, ResultAssetKey(key))

fun <T: Any> AssetComponentLoader.Context.load(type: Class<T>, key: AssetKey): T = load(getTypeInfo(type), key)
fun <T: Any> AssetComponentLoader.Context.load(type: Class<T>, key: String): T = load(getTypeInfo(type), key)
fun <T: Any> AssetComponentLoader.Context.load(type: Class<T>, key: Any): T = load(getTypeInfo(type), key)

fun <T: Any> AssetComponentLoader.Context.load(type: KClass<T>, key: AssetKey) = load(type.java, key)
fun <T: Any> AssetComponentLoader.Context.load(type: KClass<T>, key: String) = load(type.java, key)
fun <T: Any> AssetComponentLoader.Context.load(type: KClass<T>, key: Any) = load(type.java, key)

inline fun <reified T : Any> AssetComponentLoader.Context.load(key: AssetKey) = load(T::class, key)
inline fun <reified T : Any> AssetComponentLoader.Context.load(key: String) = load(T::class, key)
inline fun <reified T : Any> AssetComponentLoader.Context.load(key: Any) = load(T::class, key)
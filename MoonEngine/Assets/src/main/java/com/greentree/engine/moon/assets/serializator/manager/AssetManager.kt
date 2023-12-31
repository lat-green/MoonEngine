package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import kotlin.reflect.KClass

interface AssetManager {

	fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T>
}

fun <T : Any> AssetManager.load(cls: Class<T>, key: AssetKey): Asset<T> = load(getTypeInfo(cls), key)
fun <T : Any> AssetManager.load(cls: KClass<T>, key: AssetKey): Asset<T> = load(cls.java, key)
inline fun <reified T : Any> AssetManager.load(key: AssetKey): Asset<T> = load(T::class, key)

fun <T : Any> AssetManager.load(type: TypeInfo<T>, name: String): Asset<T> = load(type, ResourceAssetKey(name))
fun <T : Any> AssetManager.load(cls: Class<T>, name: String): Asset<T> = load(getTypeInfo(cls), ResourceAssetKey(name))
fun <T : Any> AssetManager.load(cls: KClass<T>, name: String): Asset<T> = load(cls.java, ResourceAssetKey(name))
inline fun <reified T : Any> AssetManager.load(name: String): Asset<T> = load(T::class, ResourceAssetKey(name))

fun <T : Any> AssetManager.load(type: TypeInfo<T>, result: Any): Asset<T> = load(type, ResultAssetKey(result))
fun <T : Any> AssetManager.load(cls: Class<T>, result: Any): Asset<T> = load(getTypeInfo(cls), ResultAssetKey(result))
fun <T : Any> AssetManager.load(cls: KClass<T>, result: Any): Asset<T> = load(cls.java, ResultAssetKey(result))
inline fun <reified T : Any> AssetManager.load(result: Any): Asset<T> = load(T::class, ResultAssetKey(result))


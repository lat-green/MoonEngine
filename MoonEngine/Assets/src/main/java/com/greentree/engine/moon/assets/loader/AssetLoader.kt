package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import kotlin.reflect.KClass

interface AssetLoader {

	fun <T : Any> load(context: Context, type: TypeInfo<T>, key: AssetKey): T

	interface Context {

		fun <T : Any> loadAsset(type: TypeInfo<T>, key: AssetKey): Asset<T>
	}
}

fun <T : Any> AssetLoader.Context.load(type: TypeInfo<T>, key: AssetKey) = loadAsset(type, key).value
fun <T : Any> AssetLoader.Context.load(cls: Class<T>, key: AssetKey): T = load(getTypeInfo(cls), key)
fun <T : Any> AssetLoader.Context.load(cls: Class<T>, resourceName: String): T =
	load(getTypeInfo(cls), ResourceAssetKey(resourceName))

fun <T : Any> AssetLoader.Context.load(cls: KClass<T>, key: AssetKey): T = load(getTypeInfo(cls.java), key)
fun <T : Any> AssetLoader.Context.load(cls: KClass<T>, resourceName: String): T =
	load(getTypeInfo(cls.java), ResourceAssetKey(resourceName))

inline fun <reified T : Any> AssetLoader.Context.load(key: AssetKey): T = load(getTypeInfo(T::class.java), key)
inline fun <reified T : Any> AssetLoader.Context.load(resourceName: String): T =
	load(getTypeInfo(T::class.java), ResourceAssetKey(resourceName))

fun <T : Any> AssetLoader.Context.loadAsset(cls: Class<T>, key: AssetKey): Asset<T> = loadAsset(getTypeInfo(cls), key)
fun <T : Any> AssetLoader.Context.loadAsset(cls: KClass<T>, key: AssetKey): Asset<T> =
	loadAsset(getTypeInfo(cls.java), key)

inline fun <reified T : Any> AssetLoader.Context.loadAsset(key: AssetKey): Asset<T> =
	loadAsset(getTypeInfo(T::class.java), key)
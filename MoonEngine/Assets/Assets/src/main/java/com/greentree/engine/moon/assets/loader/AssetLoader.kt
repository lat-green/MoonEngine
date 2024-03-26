package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import kotlin.reflect.KClass

interface AssetLoader {

	fun <T : Any> load(context: Context, type: TypeInfo<T>, key: AssetKey): T

	interface Context {

		fun <T : Any> loadAsset(type: TypeInfo<T>, key: AssetKey): Asset<T>
	}
}

fun <T : Any> AssetLoader.Context.load(type: TypeInfo<T>, key: AssetKey) = loadAsset(type, key).value
fun <T : Any> AssetLoader.Context.load(type: TypeInfo<T>, resourceName: String) = loadAsset(type, resourceName).value

fun <T : Any> AssetLoader.Context.load(cls: Class<T>, key: AssetKey): T = loadAsset(cls, key).value
fun <T : Any> AssetLoader.Context.load(cls: Class<T>, resourceName: String): T = loadAsset(cls, resourceName).value

fun <T : Any> AssetLoader.Context.load(cls: KClass<T>, key: AssetKey): T = loadAsset(cls, key).value
fun <T : Any> AssetLoader.Context.load(cls: KClass<T>, resourceName: String): T = loadAsset(cls, resourceName).value

inline fun <reified T : Any> AssetLoader.Context.load(key: AssetKey): T = loadAsset<T>(key).value
inline fun <reified T : Any> AssetLoader.Context.load(resourceName: String): T = loadAsset<T>(resourceName).value
inline fun <reified T : Any> AssetLoader.Context.load(any: Any): T = loadAsset<T>(any).value

fun <T : Any> AssetLoader.Context.loadAsset(type: TypeInfo<T>, resourceName: String): Asset<T> =
	loadAsset(type, ResourceAssetKey(resourceName))

fun <T : Any> AssetLoader.Context.loadAsset(cls: Class<T>, resourceName: String): Asset<T> =
	loadAsset(getTypeInfo(cls), resourceName)

fun <T : Any> AssetLoader.Context.loadAsset(cls: KClass<T>, resourceName: String): Asset<T> =
	loadAsset(getTypeInfo(cls.java), resourceName)

inline fun <reified T : Any> AssetLoader.Context.loadAsset(resourceName: String): Asset<T> =
	loadAsset(getTypeInfo(T::class.java), resourceName)

fun <T : Any> AssetLoader.Context.loadAsset(cls: Class<T>, key: AssetKey): Asset<T> = loadAsset(getTypeInfo(cls), key)

fun <T : Any> AssetLoader.Context.loadAsset(cls: KClass<T>, key: AssetKey): Asset<T> =
	loadAsset(getTypeInfo(cls.java), key)

inline fun <reified T : Any> AssetLoader.Context.loadAsset(key: AssetKey): Asset<T> =
	loadAsset(getTypeInfo(T::class.java), key)

inline fun <reified T : Any> AssetLoader.Context.loadAsset(any: Any): Asset<T> =
	loadAsset(getTypeInfo(T::class.java), ResultAssetKey(any))
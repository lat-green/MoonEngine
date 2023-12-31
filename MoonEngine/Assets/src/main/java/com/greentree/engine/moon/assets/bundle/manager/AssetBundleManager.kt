package com.greentree.engine.moon.assets.bundle.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.bundle.AssetBundle

interface AssetBundleManager {

	fun load(bundle: AssetBundle)

	fun <T : Any> load(type: TypeInfo<T>, name: String): T
	fun <T : Any> loadAsset(type: TypeInfo<T>, name: String) = object : Asset<T> {
		override val value: T
			get() = load(type, name)
		override val status: Asset.Status
			get() = Asset.Status.DONE
	}

	fun <T : Any> loadLazy(type: TypeInfo<T>, name: String) = loadAsset(type, name)
	fun <T : Any> loadAsync(type: TypeInfo<T>, name: String) = loadAsset(type, name)
	fun <T : Any> loadAsync(type: TypeInfo<T>, name: String, defaultValue: T) = loadAsset(type, name)
	fun <T : Any> loadLazyAsync(type: TypeInfo<T>, name: String) = loadAsset(type, name)

	operator fun contains(name: String): Boolean = name in names

	val names: Iterable<String>

	interface Asset<T> {

		val value: T
		val status: Status

		enum class Status(val isCanGetValueNow: Boolean) {
			DONE(true),
			LAZY(false),
			LOADING(false),
			LOADING_HAS_DEFAULT(true)
		}
	}
}

inline fun <reified T : Any> AssetBundleManager.getStream(name: String): T =
	load(TypeInfoBuilder.getTypeInfo(T::class.java), name)

inline fun <reified T : Any> AssetBundleManager.loadAsset(name: String): AssetBundleManager.Asset<T> =
	loadAsset(TypeInfoBuilder.getTypeInfo(T::class.java), name)

inline fun <reified T : Any> AssetBundleManager.loadLazy(name: String): AssetBundleManager.Asset<T> =
	loadLazy(TypeInfoBuilder.getTypeInfo(T::class.java), name)

inline fun <reified T : Any> AssetBundleManager.loadAsync(name: String, defaultValue: T): AssetBundleManager.Asset<T> =
	loadAsync(TypeInfoBuilder.getTypeInfo(T::class.java), name, defaultValue)

inline fun <reified T : Any> AssetBundleManager.loadAsync(name: String): AssetBundleManager.Asset<T> =
	loadAsync(TypeInfoBuilder.getTypeInfo(T::class.java), name)

inline fun <reified T : Any> AssetBundleManager.loadLazyAsync(name: String): AssetBundleManager.Asset<T> =
	loadLazyAsync(TypeInfoBuilder.getTypeInfo(T::class.java), name)
package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import org.apache.logging.log4j.MarkerManager

interface AssetManager {

	fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T>
	fun canLoad(type: TypeInfo<*>, key: AssetKey): Boolean

	fun <T : Any> load(cls: Class<T>, key: Any?): Asset<T> {
		return load(cls, ResultAssetKey(key))
	}

	fun <T : Any> load(cls: Class<T>, key: AssetKey): Asset<T> {
		val type = TypeInfoBuilder.getTypeInfo(cls)
		return load(type, key)
	}

	fun <T : Any> load(cls: Class<T>, resource: String): Asset<T> {
		return load(cls, ResourceAssetKey(resource))
	}

	fun <T : Any> load(type: TypeInfo<T>?, key: Any?): Asset<T> {
		return load(type, ResultAssetKey(key))
	}

	fun <T : Any> load(type: TypeInfo<T>, resource: String): Asset<T> {
		return load(type, ResourceAssetKey(resource))
	}

	fun canLoad(cls: Class<*>, key: AssetKey): Boolean {
		val type = TypeInfoBuilder.getTypeInfo(cls)
		return canLoad(type, key)
	}

	companion object {

		@JvmField
		val ASSETS = MarkerManager.getMarker("assets")
	}
}

inline fun <reified T : Any> AssetManager.canLoad(key: AssetKey): Boolean {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return canLoad(type, key)
}

inline fun <reified T : Any> AssetManager.load(key: Any?): Asset<T> {
	return load(ResultAssetKey(key))
}

inline fun <reified T : Any> AssetManager.load(key: AssetKey): Asset<T> {
	val type = TypeInfoBuilder.getTypeInfo(T::class.java)
	return load(type, key)
}

inline fun <reified T : Any> AssetManager.load(resource: String): Asset<T> {
	return load(ResourceAssetKey(resource))
}

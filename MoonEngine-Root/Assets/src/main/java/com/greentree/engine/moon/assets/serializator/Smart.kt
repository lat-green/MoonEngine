package com.greentree.engine.moon.assets.serializator

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.marker.NotMyKeyType
import kotlin.reflect.KClass

class Smart<T : Any>(private val origin: AssetSerializator<T>) : AssetSerializator<T> by origin {

	private val notMyKeyType = mutableSetOf<KClass<out AssetKey>>()

	override fun load(context: AssetManager, key: AssetKey): Asset<T>? {
		val keyClass = key::class
		if(keyClass in notMyKeyType)
			throw NotMyKeyType
		try {
			return origin.load(context, key)
		} catch(e: NotMyKeyType) {
			notMyKeyType.add(keyClass)
			throw e
		}
		return null
	}
}

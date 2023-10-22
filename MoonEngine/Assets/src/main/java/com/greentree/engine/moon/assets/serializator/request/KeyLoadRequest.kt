package com.greentree.engine.moon.assets.serializator.request

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.serializator.manager.AssetManager

interface KeyLoadRequest<T : Any> {

	fun loadType(): TypeInfo<T>

	fun type(): AssetKeyType {
		return key().type()
	}

	fun key(): AssetKey
}

fun <T : Any> AssetManager.load(request: KeyLoadRequest<T>) = load(request.loadType(), request.key())

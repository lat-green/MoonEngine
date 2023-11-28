package com.greentree.engine.moon.assets.serializator.manager.chain

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType

data class ContinuingChain(
	val handler: ChainHandler,
	val next: Chain,
) : Chain {

	override fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType) = handler.loadDefault(next, type, key)

	override fun <T : Any> loadCache(type: TypeInfo<T>, key: AssetKey) = handler.loadCache(next, type, key)

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey) = handler.load(next, type, key)
}

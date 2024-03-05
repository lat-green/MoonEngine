package com.greentree.engine.moon.assets.serializator.manager.chain

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey

class PlusChainHandler(private val context1: ChainHandler, private val context2: ChainHandler) : ChainHandler {

	override fun <T : Any> load(chain: Chain, type: TypeInfo<T>, key: AssetKey) =
		context1.load(ContinuingChain(context2, chain), type, key)

	override fun <T : Any> loadCache(chain: Chain, type: TypeInfo<T>, key: AssetKey) =
		context1.loadCache(ContinuingChain(context2, chain), type, key)

	override fun <T : Any> loadDefault(chain: Chain, type: TypeInfo<T>) =
		context1.loadDefault(ContinuingChain(context2, chain), type)
}

operator fun ChainHandler.plus(other: ChainHandler) = PlusChainHandler(this, other)


package com.greentree.engine.moon.assets.serializator.manager.chain

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType

interface ChainHandler {

	object Null : ChainHandler

	fun <T : Any> loadDefault(chain: Chain, type: TypeInfo<T>, key: AssetKeyType) = chain.loadDefault(type, key)
	fun <T : Any> loadCache(chain: Chain, type: TypeInfo<T>, key: AssetKey) = chain.loadCache(type, key)
	fun <T : Any> load(chain: Chain, type: TypeInfo<T>, key: AssetKey) = chain.load(type, key)
}

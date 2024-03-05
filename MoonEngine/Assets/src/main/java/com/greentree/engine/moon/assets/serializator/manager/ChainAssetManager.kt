package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.request.TryNotUpdate
import com.greentree.engine.moon.assets.serializator.manager.chain.Chain
import com.greentree.engine.moon.assets.serializator.manager.chain.ChainHandler
import com.greentree.engine.moon.assets.serializator.manager.chain.plus
import kotlin.reflect.KClass

interface ChainAssetManager : AssetManager {

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> =
		wrap(root.load(type, key))

	fun build(handler: ChainHandler): Chain

	fun <T : Any> wrap(provider: AssetProvider<T>): Asset<T>
}

fun <T : Any> ChainAssetManager.loadDefault(type: TypeInfo<T>) = root.loadDefault(type)

val ChainAssetManager.root
	get() = build(ChainHandler.Null)

fun ChainAssetManager.build(vararg ctx: ChainHandler): Chain = build(ctx.reduce { a, b -> a + b })

fun ChainAssetManager.canLoad(type: TypeInfo<*>, key: AssetKey) =
	Result.runCatching { build().load(type, key).value(TryNotUpdate)!! }.isSuccess

fun ChainAssetManager.canLoad(type: Class<*>, key: AssetKey) = canLoad(TypeInfoBuilder.getTypeInfo(type), key)
fun ChainAssetManager.canLoad(type: KClass<*>, key: AssetKey) = canLoad(type.java, key)
inline fun <reified T> ChainAssetManager.canLoad(key: AssetKey) = canLoad(T::class.java, key)

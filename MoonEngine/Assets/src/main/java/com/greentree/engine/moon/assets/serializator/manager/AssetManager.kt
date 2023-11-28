package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.manager.chain.Chain
import com.greentree.engine.moon.assets.serializator.manager.chain.ChainHandler
import com.greentree.engine.moon.assets.serializator.manager.chain.plus
import org.apache.logging.log4j.MarkerManager
import kotlin.reflect.KClass

interface AssetManager : AssetLoader.Context {

	fun build(ctx: ChainHandler): Chain

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> = root.load(type, key)

	companion object {

		@JvmField
		val ASSETS = MarkerManager.getMarker("assets")!!
	}
}

fun <T : Any> AssetManager.loadDefault(type: TypeInfo<T>, key: AssetKeyType) = root.loadDefault(type, key)

val AssetManager.root
	get() = build(ChainHandler.Null)

fun AssetManager.build(vararg ctx: ChainHandler): Chain = build(ctx.reduce { a, b -> a + b })

fun AssetManager.canLoad(type: TypeInfo<*>, key: AssetKey) = load(type, key).isValid()
fun AssetManager.canLoad(type: Class<*>, key: AssetKey) = canLoad(TypeInfoBuilder.getTypeInfo(type), key)
fun AssetManager.canLoad(type: KClass<*>, key: AssetKey) = canLoad(type.java, key)
inline fun <reified T> AssetManager.canLoad(key: AssetKey) = canLoad(T::class.java, key)

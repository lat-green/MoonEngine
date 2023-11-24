package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import kotlin.math.max

interface AssetComponentContext {

	fun <T> load(key: AssetComponentKey<T>): AssetComponentProvider<T> {
		val keyType = TypeInfoBuilder.getTypeInfo(key::class.java) as TypeInfo<AssetComponentKey<T>>
		val func = loadFunction(keyType)
		return func(key)
	}

	fun <T, K : AssetComponentKey<T>> loadFunction(key: TypeInfo<K>): (K) -> AssetComponentProvider<T>
}

interface AssetComponentProvider<out T> : AssetComponent<T> {

	val lastModified: Long
}

fun <T, R> AssetComponentProvider<T>.map(block: (T) -> R): AssetComponentProvider<R> = MapAssetComponent(this, block)
fun <T, R> AssetComponentProvider<Iterable<T>>.mapEach(block: (T) -> R): AssetComponentProvider<Iterable<R>> =
	map { it.map(block) }

class MapAssetComponent<T, R>(val source: AssetComponentProvider<T>, val block: (T) -> R) : AssetComponentProvider<R> {

	override val value
		get() = block(source.value)
	override val lastModified
		get() = source.lastModified
}

fun <T> AssetComponentProvider<Iterable<AssetComponentProvider<T>>>.strip(): AssetComponentProvider<Iterable<T>> =
	StripAssetComponent(this)

class StripAssetComponent<T>(val source: AssetComponentProvider<Iterable<AssetComponentProvider<T>>>) :
	AssetComponentProvider<Iterable<T>> {

	override val lastModified
		get() = max(source.lastModified, source.value.maxOf { it.lastModified })
	override val value
		get() = source.value.map { it.value }
}

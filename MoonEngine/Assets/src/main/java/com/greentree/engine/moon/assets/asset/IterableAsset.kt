package com.greentree.engine.moon.assets.asset

import java.lang.Long.*

class IterableAsset<T : Any>(private val values: Asset<Iterable<Asset<T>>>) : Asset<Iterable<T>> {

	override val value: Iterable<T>
		get() = values.value.map { it.value }
	override val lastModified: Long
		get() = max(values.lastModified, values.value.maxOf { it.lastModified })

	override fun isValid() = values.isValid()
	override fun isConst() = values.isConst()
}
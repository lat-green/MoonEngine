package com.greentree.engine.moon.assets.asset

private inline fun max(vararg elements: Long) = elements.max()

data class M2Asset<T1 : Any, T2 : Any>(val source1: Asset<T1>, val source2: Asset<T2>) :
	Asset<Group2<T1, T2>> {

	override val value
		get() = group(source1.value, source2.value)
	override val lastModified
		get() = max(source1.lastModified, source2.lastModified)

	override fun isCache() = source1.isCache() && source2.isCache()
	override fun isConst() = source1.isConst() && source2.isConst()
}

data class M3Asset<T1 : Any, T2 : Any, T3 : Any>(
	val source1: Asset<T1>,
	val source2: Asset<T2>,
	val source3: Asset<T3>,
) :
	Asset<Group3<T1, T2, T3>> {

	override val value
		get() = group(source1.value, source2.value, source3.value)
	override val lastModified
		get() = max(source1.lastModified, source2.lastModified, source3.lastModified)

	override fun isCache() = source1.isCache() && source2.isCache() && source3.isCache()
	override fun isConst() = source1.isConst() && source2.isConst() && source3.isConst()
}

data class M4Asset<T1 : Any, T2 : Any, T3 : Any, T4 : Any>(
	val source1: Asset<T1>,
	val source2: Asset<T2>,
	val source3: Asset<T3>,
	val source4: Asset<T4>,
) :
	Asset<Group4<T1, T2, T3, T4>> {

	override val value
		get() = group(source1.value, source2.value, source3.value, source4.value)
	override val lastModified
		get() = max(source1.lastModified, source2.lastModified, source3.lastModified, source4.lastModified)

	override fun isCache() = source1.isCache() && source2.isCache() && source3.isCache() && source4.isCache()
	override fun isConst() = source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst()
}

data class M5Asset<T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any>(
	val source1: Asset<T1>,
	val source2: Asset<T2>,
	val source3: Asset<T3>,
	val source4: Asset<T4>,
	val source5: Asset<T5>,
) :
	Asset<Group5<T1, T2, T3, T4, T5>> {

	override val value
		get() = group(source1.value, source2.value, source3.value, source4.value, source5.value)
	override val lastModified
		get() = max(
			source1.lastModified,
			source2.lastModified,
			source3.lastModified,
			source4.lastModified,
			source5.lastModified
		)

	override fun isCache() =
		source1.isCache() && source2.isCache() && source3.isCache() && source4.isCache() && source5.isCache()

	override fun isConst() =
		source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst() && source5.isConst()
}

data class M6Asset<T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any>(
	val source1: Asset<T1>,
	val source2: Asset<T2>,
	val source3: Asset<T3>,
	val source4: Asset<T4>,
	val source5: Asset<T5>,
	val source6: Asset<T6>,
) :
	Asset<Group6<T1, T2, T3, T4, T5, T6>> {

	override val value
		get() = group(source1.value, source2.value, source3.value, source4.value, source5.value, source6.value)
	override val lastModified
		get() = max(
			source1.lastModified,
			source2.lastModified,
			source3.lastModified,
			source4.lastModified,
			source5.lastModified,
			source6.lastModified
		)

	override fun isCache() =
		source1.isCache() && source2.isCache() && source3.isCache() && source4.isCache() && source5.isCache() && source6.isCache()

	override fun isConst() =
		source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst() && source5.isConst() && source6.isConst()
}

data class MIAsset<T : Any>(
	val source: Iterable<out Asset<T>>,
) :
	Asset<Iterable<T>> {

	override val value
		get() = source.map { it.value }
	override val lastModified
		get() = source.maxOf { it.lastModified }

	override fun isCache() =
		source.all { it.isCache() }

	override fun isConst() =
		source.all { it.isConst() }
}
package com.greentree.engine.moon.assets.asset

import com.greentree.engine.moon.assets.Group2
import com.greentree.engine.moon.assets.Group3
import com.greentree.engine.moon.assets.Group4
import com.greentree.engine.moon.assets.Group5
import com.greentree.engine.moon.assets.Group6
import com.greentree.engine.moon.assets.group

private inline fun max(vararg elements: Long) = elements.max()

inline fun <T1 : Any, T2 : Any> merge(t1: Asset<T1>, t2: Asset<T2>) = M2Asset(t1, t2)
inline fun <T1 : Any, T2 : Any, T3 : Any> merge(t1: Asset<T1>, t2: Asset<T2>, t3: Asset<T3>) = M3Asset(t1, t2, t3)
inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any> merge(t1: Asset<T1>, t2: Asset<T2>, t3: Asset<T3>, t4: Asset<T4>) =
	M4Asset(t1, t2, t3, t4)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any> merge(
	t1: Asset<T1>,
	t2: Asset<T2>,
	t3: Asset<T3>,
	t4: Asset<T4>,
	t5: Asset<T5>,
) = M5Asset(t1, t2, t3, t4, t5)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any> merge(
	t1: Asset<T1>,
	t2: Asset<T2>,
	t3: Asset<T3>,
	t4: Asset<T4>,
	t5: Asset<T5>,
	t6: Asset<T6>,
) =
	M6Asset(t1, t2, t3, t4, t5, t6)

data class M2Asset<T1 : Any, T2 : Any>(val source1: Asset<T1>, val source2: Asset<T2>) :
	Asset<Group2<T1, T2>> {

	override val value
		get() = group(source1.value, source2.value)
	override val lastModified
		get() = max(source1.lastModified, source2.lastModified)

	override fun isValid() = source1.isValid() && source2.isValid()
	override fun isConst() = source1.isConst() && source2.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2)"
	}
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

	override fun isValid() = source1.isValid() && source2.isValid() && source3.isValid()
	override fun isConst() = source1.isConst() && source2.isConst() && source3.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2, $source3)"
	}
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

	override fun isValid() = source1.isValid() && source2.isValid() && source3.isValid() && source4.isValid()
	override fun isConst() = source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2, $source3, $source4)"
	}
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

	override fun isValid() =
		source1.isValid() && source2.isValid() && source3.isValid() && source4.isValid() && source5.isValid()

	override fun isConst() =
		source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst() && source5.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2, $source3, $source4, $source5)"
	}
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

	override fun isValid() =
		source1.isValid() && source2.isValid() && source3.isValid() && source4.isValid() && source5.isValid() && source6.isValid()

	override fun isConst() =
		source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst() && source5.isConst() && source6.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2, $source3, $source4, $source5, $source6)"
	}
}

data class MIAsset<T : Any>(
	val source: Iterable<out Asset<T>>,
) :
	Asset<Iterable<T>> {

	override val value
		get() = source.map { it.value }
	override val lastModified
		get() = source.maxOf { it.lastModified }

	override fun isValid() =
		source.all { it.isValid() }

	override fun isConst() =
		source.all { it.isConst() }

	override fun toString(): String {
		return "Merge($source)"
	}
}
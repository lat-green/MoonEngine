package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.asset.Group2
import com.greentree.engine.moon.assets.asset.Group3
import com.greentree.engine.moon.assets.asset.Group4
import com.greentree.engine.moon.assets.asset.Group5
import com.greentree.engine.moon.assets.asset.Group6
import com.greentree.engine.moon.assets.asset.group
import com.greentree.engine.moon.assets.provider.context.AssetContext

private inline fun max(vararg elements: Long) = elements.max()

inline fun <T1 : Any, T2 : Any> merge(t1: AssetProvider<T1>, t2: AssetProvider<T2>) = M2AssetProvider(t1, t2)
inline fun <T1 : Any, T2 : Any, T3 : Any> merge(t1: AssetProvider<T1>, t2: AssetProvider<T2>, t3: AssetProvider<T3>) =
	M3AssetProvider(t1, t2, t3)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any> merge(
	t1: AssetProvider<T1>,
	t2: AssetProvider<T2>,
	t3: AssetProvider<T3>,
	t4: AssetProvider<T4>,
) =
	M4AssetProvider(t1, t2, t3, t4)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any> merge(
	t1: AssetProvider<T1>,
	t2: AssetProvider<T2>,
	t3: AssetProvider<T3>,
	t4: AssetProvider<T4>,
	t5: AssetProvider<T5>,
) = M5AssetProvider(t1, t2, t3, t4, t5)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any> merge(
	t1: AssetProvider<T1>,
	t2: AssetProvider<T2>,
	t3: AssetProvider<T3>,
	t4: AssetProvider<T4>,
	t5: AssetProvider<T5>,
	t6: AssetProvider<T6>,
) =
	M6AssetProvider(t1, t2, t3, t4, t5, t6)

data class M2AssetProvider<T1 : Any, T2 : Any>(val source1: AssetProvider<T1>, val source2: AssetProvider<T2>) :
	AssetProvider<Group2<T1, T2>> {

	override fun value(ctx: AssetContext) = group(source1.value(ctx), source2.value(ctx))
	override val lastModified
		get() = max(source1.lastModified, source2.lastModified)

	override fun isValid(ctx: AssetContext) = source1.isValid(ctx) && source2.isValid(ctx)
	override fun isConst() = source1.isConst() && source2.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2)"
	}
}

data class M3AssetProvider<T1 : Any, T2 : Any, T3 : Any>(
	val source1: AssetProvider<T1>,
	val source2: AssetProvider<T2>,
	val source3: AssetProvider<T3>,
) :
	AssetProvider<Group3<T1, T2, T3>> {

	override fun value(ctx: AssetContext) = group(source1.value(ctx), source2.value(ctx), source3.value(ctx))
	override val lastModified
		get() = max(source1.lastModified, source2.lastModified, source3.lastModified)

	override fun isValid(ctx: AssetContext) = source1.isValid(ctx) && source2.isValid(ctx) && source3.isValid(ctx)
	override fun isConst() = source1.isConst() && source2.isConst() && source3.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2, $source3)"
	}
}

data class M4AssetProvider<T1 : Any, T2 : Any, T3 : Any, T4 : Any>(
	val source1: AssetProvider<T1>,
	val source2: AssetProvider<T2>,
	val source3: AssetProvider<T3>,
	val source4: AssetProvider<T4>,
) :
	AssetProvider<Group4<T1, T2, T3, T4>> {

	override fun value(ctx: AssetContext) =
		group(source1.value(ctx), source2.value(ctx), source3.value(ctx), source4.value(ctx))

	override val lastModified
		get() = max(source1.lastModified, source2.lastModified, source3.lastModified, source4.lastModified)

	override fun isValid(ctx: AssetContext) =
		source1.isValid(ctx) && source2.isValid(ctx) && source3.isValid(ctx) && source4.isValid(ctx)

	override fun isConst() = source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2, $source3, $source4)"
	}
}

data class M5AssetProvider<T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any>(
	val source1: AssetProvider<T1>,
	val source2: AssetProvider<T2>,
	val source3: AssetProvider<T3>,
	val source4: AssetProvider<T4>,
	val source5: AssetProvider<T5>,
) :
	AssetProvider<Group5<T1, T2, T3, T4, T5>> {

	override fun value(ctx: AssetContext) =
		group(source1.value(ctx), source2.value(ctx), source3.value(ctx), source4.value(ctx), source5.value(ctx))

	override val lastModified
		get() = max(
			source1.lastModified,
			source2.lastModified,
			source3.lastModified,
			source4.lastModified,
			source5.lastModified
		)

	override fun isValid(ctx: AssetContext) =
		source1.isValid(ctx) && source2.isValid(ctx) && source3.isValid(ctx) && source4.isValid(ctx) && source5.isValid(
			ctx
		)

	override fun isConst() =
		source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst() && source5.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2, $source3, $source4, $source5)"
	}
}

data class M6AssetProvider<T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any>(
	val source1: AssetProvider<T1>,
	val source2: AssetProvider<T2>,
	val source3: AssetProvider<T3>,
	val source4: AssetProvider<T4>,
	val source5: AssetProvider<T5>,
	val source6: AssetProvider<T6>,
) :
	AssetProvider<Group6<T1, T2, T3, T4, T5, T6>> {

	override fun value(ctx: AssetContext) =
		group(
			source1.value(ctx),
			source2.value(ctx),
			source3.value(ctx),
			source4.value(ctx),
			source5.value(ctx),
			source6.value(ctx)
		)

	override val lastModified
		get() = max(
			source1.lastModified,
			source2.lastModified,
			source3.lastModified,
			source4.lastModified,
			source5.lastModified,
			source6.lastModified
		)

	override fun isValid(ctx: AssetContext) =
		source1.isValid(ctx) && source2.isValid(ctx) && source3.isValid(ctx) && source4.isValid(ctx) && source5.isValid(
			ctx
		) && source6.isValid(ctx)

	override fun isConst() =
		source1.isConst() && source2.isConst() && source3.isConst() && source4.isConst() && source5.isConst() && source6.isConst()

	override fun toString(): String {
		return "Merge($source1, $source2, $source3, $source4, $source5, $source6)"
	}
}

data class MIAssetProvider<T : Any>(
	val source: Iterable<out AssetProvider<T>>,
) :
	AssetProvider<Iterable<T>> {

	override fun value(ctx: AssetContext) =
		source.map { it.value(ctx) }

	override val lastModified
		get() = source.maxOf { it.lastModified }

	override fun isValid(ctx: AssetContext) =
		source.all { it.isValid(ctx) }

	override fun isConst() =
		source.all { it.isConst() }

	override fun toString(): String {
		return "Merge($source)"
	}
}
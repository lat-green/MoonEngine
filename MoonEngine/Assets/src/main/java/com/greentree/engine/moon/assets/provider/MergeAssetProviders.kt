package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.Group2
import com.greentree.engine.moon.assets.Group3
import com.greentree.engine.moon.assets.Group4
import com.greentree.engine.moon.assets.Group5
import com.greentree.engine.moon.assets.Group6
import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.group
import com.greentree.engine.moon.assets.provider.request.AssetRequest

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

	override fun value(ctx: AssetRequest) = group(source1.value(ctx), source2.value(ctx))

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			yieldAll(source1.changeHandlers)
			yieldAll(source2.changeHandlers)
		}

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

	override fun value(ctx: AssetRequest) = group(source1.value(ctx), source2.value(ctx), source3.value(ctx))

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			yieldAll(source1.changeHandlers)
			yieldAll(source2.changeHandlers)
			yieldAll(source3.changeHandlers)
		}

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

	override fun value(ctx: AssetRequest) =
		group(source1.value(ctx), source2.value(ctx), source3.value(ctx), source4.value(ctx))

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			yieldAll(source1.changeHandlers)
			yieldAll(source2.changeHandlers)
			yieldAll(source3.changeHandlers)
			yieldAll(source4.changeHandlers)
		}

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

	override fun value(ctx: AssetRequest) =
		group(
			source1.value(ctx),
			source2.value(ctx),
			source3.value(ctx),
			source4.value(ctx),
			source5.value(ctx)
		)

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			yieldAll(source1.changeHandlers)
			yieldAll(source2.changeHandlers)
			yieldAll(source3.changeHandlers)
			yieldAll(source4.changeHandlers)
			yieldAll(source5.changeHandlers)
		}

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

	override fun value(ctx: AssetRequest) =
		group(
			source1.value(ctx),
			source2.value(ctx),
			source3.value(ctx),
			source4.value(ctx),
			source5.value(ctx),
			source6.value(ctx)
		)

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			yieldAll(source1.changeHandlers)
			yieldAll(source2.changeHandlers)
			yieldAll(source3.changeHandlers)
			yieldAll(source4.changeHandlers)
			yieldAll(source5.changeHandlers)
			yieldAll(source5.changeHandlers)
		}

	override fun toString(): String {
		return "Merge($source1, $source2, $source3, $source4, $source5, $source6)"
	}
}

data class MIAssetProvider<T : Any>(
	val source: Iterable<out AssetProvider<T>>,
) :
	AssetProvider<Iterable<T>> {

	override fun value(ctx: AssetRequest) =
		source.map { it.value(ctx) }

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			for(s in source)
				yieldAll(s.changeHandlers)
		}

	override fun toString(): String {
		return "Merge($source)"
	}
}
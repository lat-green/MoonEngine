package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.asset.Group2
import com.greentree.engine.moon.assets.asset.Group3
import com.greentree.engine.moon.assets.asset.Group4
import com.greentree.engine.moon.assets.asset.Group5
import com.greentree.engine.moon.assets.asset.Group6
import com.greentree.engine.moon.assets.asset.Value1Function
import java.io.Serializable

interface AssetFunction1<in T : Any, R : Any> : (AssetProvider.Context, T) -> R, Serializable {

	fun isEverValid() = false

	override fun invoke(ctx: AssetProvider.Context, value: T): R
}

fun <T : Any, R : Any> Value1Function<T, R>.toAssetFunction() = AssetFunction1Impl(this)

class AssetFunction1Impl<T : Any, R : Any>(private val origin: Value1Function<T, R>) : AssetFunction1<T, R> {

	override fun invoke(ctx: AssetProvider.Context, value: T): R {
		val lastValue = ctx.getProperty(LastValue::class.java)
		if(lastValue.isPresent)
			return origin.applyWithDest(value, lastValue.get() as R)
		return origin(value)
	}
}

interface AssetFunction2<T1 : Any, T2 : Any, R : Any> : AssetFunction1<Group2<out T1, out T2>, R> {

	override fun invoke(ctx: AssetProvider.Context, value: Group2<out T1, out T2>): R {
		return invoke(ctx, value.t1, value.t2)
	}

	fun invoke(ctx: AssetProvider.Context, value1: T1, value2: T2): R
}

interface AssetFunction3<T1 : Any, T2 : Any, T3 : Any, R : Any> : AssetFunction1<Group3<out T1, out T2, out T3>, R> {

	override fun invoke(ctx: AssetProvider.Context, value: Group3<out T1, out T2, out T3>): R {
		return invoke(ctx, value.t1, value.t2, value.t3)
	}

	fun invoke(ctx: AssetProvider.Context, value1: T1, value2: T2, value3: T3): R
}

interface AssetFunction4<T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> :
	AssetFunction1<Group4<out T1, out T2, out T3, out T4>, R> {

	override fun invoke(ctx: AssetProvider.Context, value: Group4<out T1, out T2, out T3, out T4>): R {
		return invoke(ctx, value.t1, value.t2, value.t3, value.t4)
	}

	fun invoke(ctx: AssetProvider.Context, value1: T1, value2: T2, value3: T3, value4: T4): R
}

interface AssetFunction5<T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> :
	AssetFunction1<Group5<out T1, out T2, out T3, out T4, out T5>, R> {

	override fun invoke(
		ctx: AssetProvider.Context,
		value: Group5<out T1, out T2, out T3, out T4, out T5>,
	): R {
		return invoke(ctx, value.t1, value.t2, value.t3, value.t4, value.t5)
	}

	fun invoke(ctx: AssetProvider.Context, value1: T1, value2: T2, value3: T3, value4: T4, value5: T5): R
}

interface AssetFunction6<T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> :
	AssetFunction1<Group6<out T1, out T2, out T3, out T4, out T5, out T6>, R> {

	override fun invoke(
		ctx: AssetProvider.Context,
		value: Group6<out T1, out T2, out T3, out T4, out T5, out T6>,
	): R {
		return invoke(ctx, value.t1, value.t2, value.t3, value.t4, value.t5, value.t6)
	}

	fun invoke(ctx: AssetProvider.Context, value1: T1, value2: T2, value3: T3, value4: T4, value5: T5, value6: T6): R
}
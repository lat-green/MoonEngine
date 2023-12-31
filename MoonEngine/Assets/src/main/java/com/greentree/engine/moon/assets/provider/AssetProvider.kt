package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.Value2Function
import com.greentree.engine.moon.assets.Value3Function
import com.greentree.engine.moon.assets.Value4Function
import com.greentree.engine.moon.assets.Value5Function
import com.greentree.engine.moon.assets.Value6Function
import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.EmptyAssetRequest
import java.io.Serializable

interface AssetProvider<out T : Any> : AssetProviderCharacteristics, Serializable {

	fun value(ctx: AssetRequest = EmptyAssetRequest): T
}

interface AssetProviderCharacteristics {

	val changeHandlers: Sequence<ChangeHandler>
}

val AssetProviderCharacteristics.lastModified: Long
	get() = changeHandlers.maxOfOrNull { it.lastModified } ?: 0L

fun <T : Any, R : Any> AssetProvider<T>.map(function: Value1Function<T, R>) =
	FunctionAssetProvider(this, function.toAssetFunction())

fun <T : Any, R : Any> AssetProvider<T>.map(function: AssetFunction1<T, R>) =
	FunctionAssetProvider(this, function)

fun <T1 : Any, T2 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	function: AssetFunction2<T1, T2, R>,
) = merge(asset1, asset2).map(function)

fun <T1 : Any, T2 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	function: Value2Function<T1, T2, R>,
) = merge(asset1, asset2).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	function: AssetFunction3<T1, T2, T3, R>,
) = merge(asset1, asset2, asset3).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	function: Value3Function<T1, T2, T3, R>,
) = merge(asset1, asset2, asset3).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	function: AssetFunction4<T1, T2, T3, T4, R>,
) = merge(asset1, asset2, asset3, asset4).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	function: Value4Function<T1, T2, T3, T4, R>,
) = merge(asset1, asset2, asset3, asset4).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	asset5: AssetProvider<T5>,
	function: AssetFunction5<T1, T2, T3, T4, T5, R>,
) = merge(asset1, asset2, asset3, asset4, asset5).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	asset5: AssetProvider<T5>,
	function: Value5Function<T1, T2, T3, T4, T5, R>,
) = merge(asset1, asset2, asset3, asset4, asset5).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	asset5: AssetProvider<T5>,
	asset6: AssetProvider<T6>,
	function: Value6Function<T1, T2, T3, T4, T5, T6, R>,
) = merge(asset1, asset2, asset3, asset4, asset5, asset6).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	asset5: AssetProvider<T5>,
	asset6: AssetProvider<T6>,
	function: AssetFunction6<T1, T2, T3, T4, T5, T6, R>,
) = merge(asset1, asset2, asset3, asset4, asset5, asset6).map(function)

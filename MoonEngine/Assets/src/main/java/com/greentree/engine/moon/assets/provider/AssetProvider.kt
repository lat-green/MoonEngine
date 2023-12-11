package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.EmptyAssetRequest
import com.greentree.engine.moon.assets.provider.response.AssetResponse
import java.io.Serializable

interface AssetProvider<T : Any> : AssetProviderCharacteristics, Serializable {

	fun value(ctx: AssetRequest = EmptyAssetRequest): AssetResponse<T>
}

interface AssetProviderCharacteristics {

	val lastModified: Long
}

fun <T : Any, R : Any> AssetProvider<T>.map(function: AssetFunction1<T, R>) =
	FunctionAssetProvider(this, function)

fun <T1 : Any, T2 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	function: AssetFunction2<T1, T2, R>,
) = merge(asset1, asset2).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	function: AssetFunction3<T1, T2, T3, R>,
) = merge(asset1, asset2, asset3).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	function: AssetFunction4<T1, T2, T3, T4, R>,
) = merge(asset1, asset2, asset3, asset4).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	asset5: AssetProvider<T5>,
	function: AssetFunction5<T1, T2, T3, T4, T5, R>,
) = merge(asset1, asset2, asset3, asset4, asset5).map(function)

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	asset5: AssetProvider<T5>,
	asset6: AssetProvider<T6>,
	function: AssetFunction6<T1, T2, T3, T4, T5, T6, R>,
) = merge(asset1, asset2, asset3, asset4, asset5, asset6).map(function)

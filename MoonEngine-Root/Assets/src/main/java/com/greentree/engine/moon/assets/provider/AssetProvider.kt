package com.greentree.engine.moon.assets.provider

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.asset.AssetCharacteristics
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.kernel.ClassProperties
import java.io.Serializable

interface AssetProvider<T : Any> : AssetCharacteristics, Serializable {

	interface Manager : Property {

		fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): T
		fun <T : Any> load(type: Class<T>, key: AssetKey) =
			load(getTypeInfo(type) as TypeInfo<T>, key)
	}

	interface Context : ClassProperties<Property>

	interface Property

	val value
		get() = value(MapAssetProperties())

	fun value(ctx: Context): T
}

val AssetProvider.Context.manager
	get() = getProperty<AssetProvider.Manager>()

inline fun <reified T : AssetProvider.Property> AssetProvider.Context.getProperty() = getProperty(T::class.java)
inline fun <reified T : Any> AssetProvider.Manager.load(key: AssetKey) = load(T::class.java, key)

inline fun <T : Any, R : Any> AssetProvider<T>.map(function: AssetFunction1<T, R>) =
	FunctionAssetProvider(this, function)

inline fun <T1 : Any, T2 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	function: AssetFunction2<T1, T2, R>,
) = merge(asset1, asset2).map(function)

inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	function: AssetFunction3<T1, T2, T3, R>,
) = merge(asset1, asset2, asset3).map(function)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	function: AssetFunction4<T1, T2, T3, T4, R>,
) = merge(asset1, asset2, asset3, asset4).map(function)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	asset5: AssetProvider<T5>,
	function: AssetFunction5<T1, T2, T3, T4, T5, R>,
) = merge(asset1, asset2, asset3, asset4, asset5).map(function)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> map(
	asset1: AssetProvider<T1>,
	asset2: AssetProvider<T2>,
	asset3: AssetProvider<T3>,
	asset4: AssetProvider<T4>,
	asset5: AssetProvider<T5>,
	asset6: AssetProvider<T6>,
	function: AssetFunction6<T1, T2, T3, T4, T5, T6, R>,
) = merge(asset1, asset2, asset3, asset4, asset5, asset6).map(function)

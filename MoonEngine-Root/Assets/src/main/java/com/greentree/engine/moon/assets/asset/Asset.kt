package com.greentree.engine.moon.assets.asset

import java.io.Serializable

interface Asset<out T : Any> : Serializable {

	val cache: T
		get() = value
	val value: T
	val lastModified: Long

	fun isValid(): Boolean = true
	fun isConst(): Boolean = false
}

fun Asset<*>.toSplitString(): String {
	val text = toString()
	val builder = StringBuilder()
	var tab = 0
	var info = 0
	var i = 0
	while(i < text.length) {
		val c = text[i]
		when(c) {
			'[' -> {
				info++
				builder.append('[')
			}

			']' -> {
				info--
				builder.append(']')
			}

			'(' -> {
				if(info == 0) {
					tab++
					builder.append("\n")
					builder.append("\t".repeat(tab))
				} else {
					builder.append('(')
				}
			}

			')' -> {
				if(info == 0) {
					tab--
				} else {
					builder.append(')')
				}
			}

			',' -> {
				if(info == 0) {
					builder.append("\n")
					builder.append("\t".repeat(tab))
					while(i + 1 < text.length && text[i + 1] == ' ') i++
				} else {
					builder.append(',')
				}
			}

			else -> {
				builder.append(c)
			}
		}
		i++
	}
	return builder.toString()
}

inline val Asset<*>.isValid
	get() = isValid()
inline val Asset<*>.isConst
	get() = isConst()

inline fun <T : Any, R : Any> Asset<T>.map(function: Value1Function<T, R>) =
	ValueFunctionAsset.newAsset(this, function)

inline fun <T : Any, R : Any, F> Asset<T>.map(function: F) where F : (T) -> R, F : Serializable =
	map(Value1FunctionImpl(function))

inline fun <T1 : Any, T2 : Any, R : Any> map(
	asset1: Asset<T1>,
	asset2: Asset<T2>,
	function: Value2Function<T1, T2, R>,
) = merge(asset1, asset2).map(function)

inline fun <T1 : Any, T2 : Any, R : Any, F> map(
	asset1: Asset<T1>,
	asset2: Asset<T2>,
	function: F,
) where F : (Group2<T1, T2>) -> R, F : Serializable = merge(asset1, asset2).map(function)

inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> map(
	asset1: Asset<T1>,
	asset2: Asset<T2>,
	asset3: Asset<T3>,
	function: Value3Function<T1, T2, T3, R>,
) = merge(asset1, asset2, asset3).map(function)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> map(
	asset1: Asset<T1>,
	asset2: Asset<T2>,
	asset3: Asset<T3>,
	asset4: Asset<T4>,
	function: Value4Function<T1, T2, T3, T4, R>,
) = merge(asset1, asset2, asset3, asset4).map(function)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> map(
	asset1: Asset<T1>,
	asset2: Asset<T2>,
	asset3: Asset<T3>,
	asset4: Asset<T4>,
	asset5: Asset<T5>,
	function: Value5Function<T1, T2, T3, T4, T5, R>,
) = merge(asset1, asset2, asset3, asset4, asset5).map(function)

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> map(
	asset1: Asset<T1>,
	asset2: Asset<T2>,
	asset3: Asset<T3>,
	asset4: Asset<T4>,
	asset5: Asset<T5>,
	asset6: Asset<T6>,
	function: Value6Function<T1, T2, T3, T4, T5, T6, R>,
) = merge(asset1, asset2, asset3, asset4, asset5, asset6).map(function)

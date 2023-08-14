package com.greentree.engine.moon.assets.asset

import java.io.Serializable

interface SmartFunction<T : Any, R : Any> : Serializable {

	fun apply(value: T): ApplyContext<T, R>
}

class ValueSmartFunction<T : Any, R : Any>(private val origin: Value1Function<T, R>) : SmartFunction<T, R> {

	override fun apply(value: T): ApplyContext<T, R> {
		return object : ApplyContext<T, R> {
			override val result: Result<R>
				get() {
					val result = origin.apply(value)
					return try {
						ValueResult(result)
					} catch(e: Exception) {
						ErrorResult(e)
					}
				}
		}
	}
}

operator fun <T : Any, R : Any> SmartFunction<T, R>.invoke(value: T): ApplyContext<T, R> =
	RepeatApplyContext(apply(value))

sealed interface Result<R : Any>
data class ValueResult<R : Any>(val result: R) : Result<R>
data class RepeatResult<R : Any>(val exception: Exception? = null) : Result<R>
data class ErrorResult<R : Any>(val exception: Exception? = null) : Result<R>

val <R : Any> Result<R>.result: R
	get() = (this as ValueResult<R>).result

interface ApplyContext<T : Any, R : Any> {

	val result: Result<R>
}

class RepeatApplyContext<T : Any, R : Any>(private val origin: ApplyContext<T, R>) : ApplyContext<T, R> {

	override val result: Result<R>
		get() {
			return try {
				origin.result
			} catch(e: Exception) {
				RepeatResult(e)
			}
		}
}

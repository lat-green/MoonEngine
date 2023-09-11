package com.greentree.engine.moon.assets.asset

import org.apache.logging.log4j.LogManager
import java.io.Serializable

private val LOG = LogManager.getLogger()

interface Value1Function<T : Any, R : Any> : (T) -> R, Serializable {

	fun isEverValid() = false

	override operator fun invoke(p1: T) = apply(p1)

	fun apply(value: T): R
	fun applyWithDest(value: T, dest: R): R = apply(value)
}

operator fun <T : Any, R : Any> Value1Function<T, R>.invoke(value: T, dest: R) = applyWithDest(value, dest)

class Value1FunctionImpl<T : Any, R : Any, F>(private val function: F) : Value1Function<T, R>
		where
F : (T) -> R,
F : Serializable {

	override fun apply(value: T) = function(value)
}

interface Value2Function<T1 : Any, T2 : Any, R : Any> : Value1Function<Group2<out T1, out T2>, R> {

	override fun apply(value: Group2<out T1, out T2>): R {
		return apply(value.t1, value.t2)
	}

	fun apply(value1: T1, value2: T2): R
}

interface Value3Function<T1 : Any, T2 : Any, T3 : Any, R : Any> : Value1Function<Group3<out T1, out T2, out T3>, R> {

	override fun apply(value: Group3<out T1, out T2, out T3>): R {
		return apply(value.t1, value.t2, value.t3)
	}

	fun apply(value1: T1, value2: T2, value3: T3): R
}

interface Value4Function<T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> :
	Value1Function<Group4<out T1, out T2, out T3, out T4>, R> {

	override fun apply(value: Group4<out T1, out T2, out T3, out T4>): R {
		return apply(value.t1, value.t2, value.t3, value.t4)
	}

	fun apply(value1: T1, value2: T2, value3: T3, value4: T4): R
}

interface Value5Function<T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> :
	Value1Function<Group5<out T1, out T2, out T3, out T4, out T5>, R> {

	override fun apply(
		value: Group5<out T1, out T2, out T3, out T4, out T5>,
	): R {
		return apply(value.t1, value.t2, value.t3, value.t4, value.t5)
	}

	fun apply(value1: T1, value2: T2, value3: T3, value4: T4, value5: T5): R
}

interface Value6Function<T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> :
	Value1Function<Group6<out T1, out T2, out T3, out T4, out T5, out T6>, R> {

	override fun apply(
		value: Group6<out T1, out T2, out T3, out T4, out T5, out T6>,
	): R {
		return apply(value.t1, value.t2, value.t3, value.t4, value.t5, value.t6)
	}

	fun apply(value1: T1, value2: T2, value3: T3, value4: T4, value5: T5, value6: T6): R
}
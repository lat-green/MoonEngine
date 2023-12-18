package com.greentree.engine.moon.assets

import kotlin.reflect.KProperty

interface FormBuilder {

	fun inputPath() = inputString("path")
	
	fun inputString(name: String): Ref<String>
	fun inputInt(name: String): Ref<Int>
	fun inputRadio(name: String, values: Iterable<String>): Ref<String>
	fun inputBoolean(name: String): Ref<Boolean>
}

fun FormBuilder.inputRadio(name: String, vararg values: String): Ref<String> = inputRadio(name, listOf(*values))

inline fun <reified T : Enum<T>> FormBuilder.inputEnum(name: String): Ref<T> = ref {
	val constants = T::class.java.enumConstants
	val name by inputRadio(name, constants.map { it.name })
	for(c in constants)
		if(c.name == name)
			return@ref c
	TODO()
}

interface Ref<T> {

	val value: T
}

fun <T> ref(block: () -> T) = LazyRef(block)

class LazyRef<T>(private val valueGet: () -> T) : Ref<T> {

	override val value by lazy(valueGet)
}

data class DataRef<T>(override val value: T) : Ref<T>

fun <T> Ref(value: T) = DataRef(value)

operator fun <T> Ref<T>.getValue(thisRef: Nothing?, property: KProperty<*>) = value


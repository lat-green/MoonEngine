package com.greentree.engine.moon.assets

import java.io.Serializable
import kotlin.reflect.KProperty

interface Asset<out T : Any> : Serializable {

	val value: T
}

operator fun <T : Any> Asset<T>.getValue(
	thisRef: Any?,
	property: KProperty<*>,
) = value
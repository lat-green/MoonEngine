package com.greentree.engine.moon.assets.react

import java.io.Serializable
import kotlin.reflect.KProperty

data class Ref<T>(var value: T) : Serializable

operator fun <T> Ref<T>.getValue(thisRef: Any?, property: KProperty<*>) = value
operator fun <T> Ref<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
	this.value = value
}

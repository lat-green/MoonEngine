package com.greentree.engine.moon.assets.react

import kotlin.reflect.KProperty

interface State<T> {

	var value: T
}

operator fun <T> State<T>.getValue(thisRef: Any?, property: KProperty<*>) = value
operator fun <T> State<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
	this.value = value
}
package com.greentree.engine.moon.assets.react

interface State<T> {

	val value: T
}

interface MutableState<T> : State<T> {

	override var value: T
}

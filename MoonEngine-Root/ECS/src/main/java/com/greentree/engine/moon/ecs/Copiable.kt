package com.greentree.engine.moon.ecs

interface Copiable<T : Copiable<T>> {

	fun copy(): T
	fun copyTo(copy: T)
}
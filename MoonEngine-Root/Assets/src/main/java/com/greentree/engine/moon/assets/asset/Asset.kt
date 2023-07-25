package com.greentree.engine.moon.assets.asset

import java.io.Serializable

interface Asset<T : Any> : Serializable {

	val value: T
	val lastModified: Long

	fun isConst(): Boolean = false
	fun isCache(): Boolean = false
}
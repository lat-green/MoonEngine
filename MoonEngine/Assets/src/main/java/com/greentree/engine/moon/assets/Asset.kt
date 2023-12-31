package com.greentree.engine.moon.assets

import java.io.Serializable

interface Asset<out T : Any> : Serializable {

	val value: T
}

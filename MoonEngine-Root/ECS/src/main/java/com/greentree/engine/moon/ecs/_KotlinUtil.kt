package com.greentree.engine.moon.ecs

fun <T : AutoCloseable, R> withUse(closeable: T, function: T.() -> R): R {
	return closeable.use(function)
}

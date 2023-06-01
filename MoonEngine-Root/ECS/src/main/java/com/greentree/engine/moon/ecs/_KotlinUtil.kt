package com.greentree.engine.moon.ecs

inline fun <T : AutoCloseable, R> withUse(closeable: T, function: T.() -> R): R {
	return closeable.use(function)
}

inline fun <T : AutoCloseable, R> runUse(closeable: T, function: (T) -> R): R {
	return closeable.use(function)
}

inline fun <T : AutoCloseable, R> use(closeable: T, function: (T) -> R): R {
	return closeable.use(function)
}

inline fun repeat(times: Int, action: () -> Unit) {
	var t = times
	while (t-- > 0) {
		action()
	}
}
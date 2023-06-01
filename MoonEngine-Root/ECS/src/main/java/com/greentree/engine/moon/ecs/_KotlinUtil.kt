package com.greentree.engine.moon.ecs

import com.greentree.commons.util.iterator.IteratorUtil
import java.util.function.Function
import java.util.function.Predicate

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

inline fun <T> Iterator<T>.filter(crossinline predicate: (T) -> Boolean): Iterator<T> {
	return IteratorUtil.filter(this, Predicate<T> { t -> predicate(t) })
}

inline fun <T, R> Iterator<T>.map(crossinline function: (T) -> R): Iterator<R> {
	return IteratorUtil.map(this, Function<T, R> { t -> function(t) })
}

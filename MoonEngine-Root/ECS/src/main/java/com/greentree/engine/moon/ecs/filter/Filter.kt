package com.greentree.engine.moon.ecs.filter

import com.greentree.commons.util.iterator.IteratorUtil
import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component
import java.util.AbstractMap.*
import java.util.function.Predicate

interface Filter<T : WorldEntity> : Iterable<T> {

	fun filter(predicate: (T) -> Boolean) = (this as Iterable<T>).filterLazy(predicate).toFilter()

	fun isRequired(componentClass: Class<out Component>): Boolean
	fun isIgnored(componentClass: Class<out Component>): Boolean
}

data class AllEntityFilter(private val world: World) : Filter<WorldEntity> {

	override fun iterator(): Iterator<WorldEntity> {
		return world.iterator()
	}

	override fun isRequired(componentClass: Class<out Component>) = false
	override fun isIgnored(componentClass: Class<out Component>) = false
}

fun <T, R> Iterable<T>.toMap(function: (T) -> R): Map<T, R> {
	return object : AbstractMap<T, R>() {
		override val entries: Set<Map.Entry<T, R>>
			get() = this@toMap.map { SimpleEntry(it, function(it)) }.toSet()
	}
}

fun <T, R> Map<T, R>.inverse(): Map<R, Collection<T>> {
	return object : AbstractMap<R, Collection<T>>() {
		override val entries: Set<Map.Entry<R, Collection<T>>>
			get() {
				TODO()
			}
	}
}

@Deprecated("")
fun <T : WorldEntity> Iterable<T>.toFilter(): Filter<T> {
	val ts = this
	return object : Filter<T> {
		override fun iterator(): Iterator<T> {
			return ts.iterator()
		}

		override fun isRequired(componentClass: Class<out Component>) = TODO()
		override fun isIgnored(componentClass: Class<out Component>) = TODO()
	}
}

inline fun <T : WorldEntity> Filter<T>.filter(crossinline predicate: (T) -> Boolean): Filter<T> {
	return (this as Iterable<T>).filter(predicate).toFilter()
}

inline fun <T> Iterable<T>.filterLazy(crossinline predicate: (T) -> Boolean): Iterable<T> {
	return IteratorUtil.filter(this, Predicate { predicate(it) })
}

inline val <T> Iterable<T>.size
	get() = IteratorUtil.size(this)
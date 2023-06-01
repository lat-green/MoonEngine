package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component

interface FilterBuilderBase<B : FilterBuilderBase<B>> {

	fun build(): Iterable<out WorldEntity>

	fun ignore(ignoreClass: Class<out Component>): B

	fun isRequired(componentClass: Class<out Component>): Boolean
	fun isIgnore(componentClass: Class<out Component>): Boolean
}

fun <B : FilterBuilderBase<B>> B.ignore(vararg ignoreClasses: Class<out Component>): B {
	var result = this
	for (cls in ignoreClasses)
		result = result.ignore(cls)
	return result
}
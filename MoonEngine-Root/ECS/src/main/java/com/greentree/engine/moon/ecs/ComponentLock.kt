package com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.component.Component

interface ComponentLock : AutoCloseable {

	override fun close()
	fun remove(componentClass: Class<out Component>)
	fun add(component: Component)
	operator fun <C : Component> get(componentClass: Class<C>): C
}
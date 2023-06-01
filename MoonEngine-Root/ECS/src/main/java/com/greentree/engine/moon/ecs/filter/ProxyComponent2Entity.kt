package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component

open class ProxyComponent2Entity<C1 : Component, C2 : Component>(
	private val origin: WorldEntity,
	private val component1Class: Class<C1>,
	private val component2Class: Class<C2>,
) : ProxyComponent1Entity<C1>(origin, component1Class),
	Component2Entity<C1, C2> {

	override fun component2() = get(component2Class)
}
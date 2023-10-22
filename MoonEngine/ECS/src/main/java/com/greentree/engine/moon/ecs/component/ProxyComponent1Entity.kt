package com.greentree.engine.moon.ecs.component

import com.greentree.engine.moon.ecs.WorldEntity

open class ProxyComponent1Entity<C1 : Component>(
	private val origin: WorldEntity,
	private val component1Class: Class<C1>,
) :
	Component1Entity<C1>, WorldEntity by origin {

	override fun component1() = get(component1Class)
}
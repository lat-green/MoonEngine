package com.greentree.engine.moon.ecs.component

import com.greentree.engine.moon.ecs.WorldEntity

open class ProxyComponent3Entity<C1 : Component, C2 : Component, C3 : Component>(
	private val origin: WorldEntity,
	private val component1Class: Class<C1>,
	private val component2Class: Class<C2>,
	private val component3Class: Class<C3>,
) : ProxyComponent2Entity<C1, C2>(origin, component1Class, component2Class),
	Component3Entity<C1, C2, C3> {

	override fun component3() = get(component3Class)
}
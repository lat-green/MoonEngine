package com.greentree.engine.moon.ecs.component

import com.greentree.engine.moon.ecs.WorldEntity

open class ProxyComponent4Entity<C1 : Component, C2 : Component, C3 : Component, C4 : Component>(
	private val origin: WorldEntity,
	private val component1Class: Class<C1>,
	private val component2Class: Class<C2>,
	private val component3Class: Class<C3>,
	private val component4Class: Class<C4>,
) : ProxyComponent3Entity<C1, C2, C3>(origin, component1Class, component2Class, component3Class),
	Component4Entity<C1, C2, C3, C4> {

	override fun component4() = get(component4Class)
}
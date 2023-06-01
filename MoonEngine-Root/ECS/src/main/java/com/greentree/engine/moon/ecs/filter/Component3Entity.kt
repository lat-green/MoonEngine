package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component

interface Component3Entity<C1 : Component, C2 : Component, C3 : Component> : WorldEntity {

	operator fun component1(): C1
	operator fun component2(): C2
	operator fun component3(): C3
}

package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component

interface Component2Entity<C1 : Component, C2 : Component> : WorldEntity {

	operator fun component1(): C1
	operator fun component2(): C2
}

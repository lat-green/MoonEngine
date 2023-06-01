package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.Entity
import com.greentree.engine.moon.ecs.component.Component

interface Component2Entity<C1 : Component, C2 : Component> : Entity {

	operator fun component1(): C1
	operator fun component2(): C2
}

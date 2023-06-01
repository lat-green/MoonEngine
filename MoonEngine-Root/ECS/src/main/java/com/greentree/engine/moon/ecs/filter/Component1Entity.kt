package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.Entity
import com.greentree.engine.moon.ecs.component.Component

interface Component1Entity<C1 : Component> : Entity {

	operator fun component1(): C1
}

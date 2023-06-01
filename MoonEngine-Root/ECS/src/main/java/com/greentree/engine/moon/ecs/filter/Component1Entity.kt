package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component

interface Component1Entity<C1 : Component> : WorldEntity {

	operator fun component1(): C1
}

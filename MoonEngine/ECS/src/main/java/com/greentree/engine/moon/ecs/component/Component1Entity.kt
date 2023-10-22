package com.greentree.engine.moon.ecs.component

import com.greentree.engine.moon.ecs.WorldEntity

interface Component1Entity<C1 : Component> : WorldEntity {

	operator fun component1(): C1
}

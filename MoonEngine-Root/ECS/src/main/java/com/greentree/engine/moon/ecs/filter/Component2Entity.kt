package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

interface Component2Entity<C1 : Component, C2 : Component> : Component1Entity<C1> {

	operator fun component2(): C2
}

package com.greentree.engine.moon.ecs.component

interface Component2Entity<C1 : Component, C2 : Component> : Component1Entity<C1> {

	operator fun component2(): C2
}

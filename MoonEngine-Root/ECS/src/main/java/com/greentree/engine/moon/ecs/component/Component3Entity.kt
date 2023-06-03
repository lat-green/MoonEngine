package com.greentree.engine.moon.ecs.component

interface Component3Entity<C1 : Component, C2 : Component, C3 : Component> : Component2Entity<C1, C2> {

	operator fun component3(): C3
}

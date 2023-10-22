package com.greentree.engine.moon.ecs.component

interface Component4Entity<C1 : Component, C2 : Component, C3 : Component, C4 : Component> :
	Component3Entity<C1, C2, C3> {

	operator fun component4(): C4
}

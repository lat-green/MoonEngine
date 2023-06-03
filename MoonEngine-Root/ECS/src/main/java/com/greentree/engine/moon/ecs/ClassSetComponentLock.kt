package com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.ClassSet.*
import com.greentree.engine.moon.ecs.component.Component

data class ClassSetComponentLock(val lock: LockClassSet<Component>) : ComponentLock {

	override fun close() {
		lock.close()
	}

	override fun remove(componentClass: Class<out Component>) {
		lock.remove(componentClass)
	}

	override fun add(component: Component) {
		lock.add(component)
	}
}
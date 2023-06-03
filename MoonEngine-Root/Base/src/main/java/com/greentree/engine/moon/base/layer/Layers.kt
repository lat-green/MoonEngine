package com.greentree.engine.moon.base.layer

import com.greentree.engine.moon.base.component.CreateSystem
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.scene.SceneProperty

@CreateSystem(LayerSystem::class)
data class Layers(val filter: Iterable<out WorldEntity>) : SceneProperty, Iterable<String?> {

	override fun iterator(): Iterator<String> {
		return filter.map { e -> e[Layer::class.java].name }.iterator()
	}
}
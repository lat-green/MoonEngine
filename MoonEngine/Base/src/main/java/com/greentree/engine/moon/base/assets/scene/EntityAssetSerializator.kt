package com.greentree.engine.moon.base.assets.scene

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.ecs.ClassSetEntity
import com.greentree.engine.moon.ecs.PrototypeEntity
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.lock

object EntityAssetSerializator : AssetSerializator<PrototypeEntity> {

	private val TYPE: TypeInfo<Iterable<Component>> = getTypeInfo(Iterable::class.java, Component::class.java)

	override fun load(manager: AssetLoader.Context, key: AssetKey): PrototypeEntity {
		val components = manager.load(TYPE, key)
		val entity = ClassSetEntity()
		entity.lock {
			for(c in components)
				add(c)
		}
		return entity
	}
}

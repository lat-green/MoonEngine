package com.greentree.engine.moon.base.assets.scene

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.ecs.ClassSetEntity
import com.greentree.engine.moon.ecs.PrototypeEntity
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.lock

object EntityAssetSerializator : AssetSerializator<PrototypeEntity> {

	private val TYPE: TypeInfo<Iterable<Component>> = getTypeInfo(Iterable::class.java, Component::class.java)

	override fun load(manager: AssetManager, key: AssetKey): Asset<PrototypeEntity> {
		val components = manager.load(TYPE, key)
		return components.map(ComponentsToEntity)
	}

	object ComponentsToEntity : Value1Function<Iterable<Component>, PrototypeEntity> {

		override fun applyWithDest(components: Iterable<Component>, entity: PrototypeEntity): PrototypeEntity {
			entity.clear()
			return addEntities(entity, components)
		}

		override fun apply(components: Iterable<Component>): PrototypeEntity {
			val entity = ClassSetEntity()
			return addEntities(entity, components)
		}

		private fun addEntities(entity: PrototypeEntity, components: Iterable<Component>): PrototypeEntity {
			entity.lock {
				for(c in components)
					add(c)
			}
			return entity
		}
	}
}

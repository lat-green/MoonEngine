package com.greentree.engine.moon.ecs

import com.greentree.commons.util.iterator.SizedIterable
import com.greentree.engine.moon.ecs.component.Component
import java.io.IOException
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.io.OutputStream

@JvmDefaultWithoutCompatibility
sealed interface Entity : SizedIterable<Component>, Copiable<Entity> {

	fun add(component: Component) {
		lock { add(component) }
	}

	override fun copy(): PrototypeEntity

	fun clear()

	fun copy(world: World): WorldEntity {
		val copy = world.newEntity()
		copyTo(copy)
		return copy
	}

	override fun copyTo(clone: Entity) {
		clone.lock {
			for (c in clone) {
				val cls = c.javaClass
				if (!contains(cls))
					remove(c.javaClass)
			}
		}
		runUse(clone.lock()) { lock ->
			for (c in this) {
				val cls = c.javaClass
				if (clone.contains(cls)) if (!c.copyTo(clone.get(cls))) lock.remove(cls)
			}
		}
		runUse(clone.lock()) { lock ->
			for (c in this) {
				val cls: Class<out Component?> = c.javaClass
				if (!clone.contains(cls)) lock.add(c.copy())
			}
		}
	}

	operator fun contains(componentClass: Class<out Component>): Boolean
	operator fun contains(component: Component): Boolean

	operator fun <T : Component> get(componentClass: Class<T>): T

	fun isEmpty(): Boolean

	fun <R> lock(function: ComponentLock.() -> R): R {
		return lock().use(function)
	}

	fun lock(): ComponentLock

	fun remove(componentClass: Class<out Component>) {
		lock {
			remove(componentClass)
		}
	}

	@Throws(IOException::class)
	fun save(output: OutputStream) {
		if (output is ObjectOutput)
			return save(output as ObjectOutput)
		return save(ObjectOutputStream(output) as ObjectOutput)
	}

	@Throws(IOException::class)
	fun save(output: ObjectOutput)
}

interface WorldEntity : Entity {

	fun world(): World

	fun delete()
	fun isDeleted(): Boolean

	fun activate()
	fun deactivate()

	fun isActive(): Boolean
	fun isDeactivate() = !isActive()
}

interface PrototypeEntity : Entity
package com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.kernel.runUse
import com.greentree.engine.moon.kernel.use
import java.io.IOException
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.io.OutputStream

@JvmDefaultWithoutCompatibility
sealed interface Entity : Iterable<Component>, Copiable<Entity> {

	fun add(component: Component)

	override fun copy(): PrototypeEntity

	fun clear() {
		lock {
			for (c in this@Entity)
				remove(c::class.java)
		}
	}

	fun size(): Int

	fun copy(world: World): WorldEntity {
		val copy = world.newEntity()
		copyTo(copy)
		return copy
	}

	override fun copyTo(clone: Entity): Boolean {
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
		return true
	}

	operator fun contains(componentClass: Class<out Component>): Boolean
	operator fun contains(component: Component): Boolean

	operator fun <T : Component> get(componentClass: Class<T>): T

	fun isEmpty(): Boolean = size() == 0

	fun lock(): ComponentLock {
		return object : ComponentLock {
			override fun close() {
			}

			override fun remove(componentClass: Class<out Component>) {
				this@Entity.remove(componentClass)
			}

			override fun add(component: Component) {
				this@Entity.add(component)
			}
		}
	}

	fun remove(componentClass: Class<out Component>)

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
	fun isDeactivate() = !isDeleted() && !isActive()
}

interface PrototypeEntity : Entity

fun <R> Entity.lock(function: ComponentLock.() -> R): R {
	return use(lock(), function)
}
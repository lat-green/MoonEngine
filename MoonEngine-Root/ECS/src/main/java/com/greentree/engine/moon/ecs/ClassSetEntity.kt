package com.greentree.engine.moon.ecs

import com.greentree.commons.util.iterator.IteratorUtil
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.kernel.AnnotationUtil
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.util.stream.Stream

class ClassSetEntity : PrototypeEntity, Cloneable {

	private val components = ComponentClassSet()

	override fun clear() {
		components.clear()
	}

	override fun contains(componentClass: Class<out Component>): Boolean {
		return components.contains(componentClass)
	}

	override fun contains(component: Component): Boolean {
		return components.contains(component)
	}

	override fun <T : Component> get(componentClass: Class<T>): T {
		return components[componentClass]
	}

	override fun copy(): ClassSetEntity {
		val clone = ClassSetEntity()
		copyTo(clone)
		return clone
	}

	override fun isEmpty(): Boolean {
		return components.isEmpty
	}

	override fun iterator(): MutableIterator<Component> {
		return components.iterator()
	}

	override fun lock(): ComponentLock {
		return ClassSetComponentLock(components.lock())
	}

	override fun size(): Int {
		return components.size()
	}

	override fun toString(): String {
		return "Entity " + IteratorUtil.toString(components)
	}

	override fun save(output: ObjectOutput) {
		output.writeInt(components.size())
		for (c in components)
			output.writeObject(c)
	}

	class ComponentClassSet : ClassSet<Component>() {

		override fun getClassRequired(
			cls: Class<out Component>,
		): Iterable<Class<out Component>> {
			return getRequiredComponent(cls).toList()
		}

		companion object {

			private const val serialVersionUID = 1L

			fun getRequiredComponent(cls: Class<*>): Stream<Class<out Component>> {
				val requiredComponents = AnnotationUtil.getAnnotation(cls, RequiredComponent::class.java)
				return requiredComponents.stream().flatMap { Stream.of(*it.value) }.map { it.java }
			}
		}
	}

	companion object {

		@JvmOverloads
		@JvmStatic
		fun load(bytes: ByteArray, offset: Int = 0, length: Int = bytes.size): ClassSetEntity {
			return use(ByteArrayInputStream(bytes, offset, length)) {
				load(it)
			}
		}

		@JvmStatic
		fun load(input: InputStream): ClassSetEntity {
			if (input is ObjectInput)
				return load(input as ObjectInput)
			return load(ObjectInputStream(input) as ObjectInput)
		}

		@JvmStatic
		fun load(input: ObjectInput): ClassSetEntity {
			val result = ClassSetEntity()
			use(result.components.lock()) { lock ->
				repeat(input.readInt()) {
					lock.add(input.readObject() as Component)
				}
			}
			return result
		}
	}
}
package com.greentree.engine.moon.ecs

import com.greentree.commons.action.observable.TypedObjectObservable
import com.greentree.commons.action.observer.type.TypedObjectAction
import com.greentree.commons.util.collection.OneClassSet
import com.greentree.commons.util.iterator.IteratorUtil
import java.io.Externalizable
import java.io.IOException
import java.io.ObjectInput
import java.io.ObjectOutput
import java.util.*

@Suppress("deprecation")
open class ClassSet<E : Any> : Iterable<E>, Externalizable {

	@Transient
	private val addAction = TypedObjectAction<Class<out E>, E>()

	@Transient
	private val removeAction = TypedObjectAction<Class<out E>, E>()
	private val components = OneClassSet<E>()

	fun clear() {
		lockWith {
			for (c in components)
				remove(c::class.java)
		}
	}

	val isEmpty
		get() = components.isEmpty()

	operator fun contains(componentClass: Class<out E>): Boolean {
		return components.containsClass(componentClass)
	}

	operator fun contains(component: E): Boolean {
		return components.contains(component)
	}

	override fun toString(): String {
		return "ClassSet $components"
	}

	operator fun <T : E> get(componentClass: Class<T>): T {
		require(contains(componentClass)) { "componenet " + componentClass.name + " not contains in " + this }
		return components.get(componentClass)
	}

	fun getAddAction(): TypedObjectObservable<out Class<out E>, out E> {
		return addAction
	}

	fun getRemoveAction(): TypedObjectObservable<out Class<out E>, out E> {
		return removeAction
	}

	override fun iterator(): MutableIterator<E> {
		return components.iterator()
	}

	private fun <R> lockWith(function: LockClassSet<E>.() -> R): R {
		return lock().use(function)
	}

	private fun <R> lockUse(function: (LockClassSet<E>) -> R): R {
		return lock().use(function)
	}

	fun lock(): LockClassSet<E> {
		return LockClassSet(this)
	}

	@Throws(IOException::class, ClassNotFoundException::class)
	override fun readExternal(objectInput: ObjectInput) {
		var size = objectInput.readInt()
		lockUse { lock ->
			while (size-- > 0) {
				val c = objectInput.readObject() as E
				lock.add(c)
			}
		}
	}

	fun size(): Int {
		return components.size
	}

	@Throws(IOException::class)
	override fun writeExternal(out: ObjectOutput) {
		out.writeInt(components.size)
		for (c in this) out.writeObject(c)
	}

	class LockClassSet<E : Any>(private val _this: ClassSet<E>) : AutoCloseable {

		private val addedComponent: MutableCollection<E> = HashSet()
		private val removedComponent: MutableCollection<E> = HashSet()

		fun add(component: E) {
			if (removedComponent.contains(component))
				removedComponent.remove(component)
			else {
				val cls = component::class
				require(!addedComponent.contains(component)) { "$cls aready added" }
				addedComponent.add(component)
			}
		}

		override fun close() {
			for (c in addedComponent) {
				val cls = c.javaClass as Class<out E>
				require(!_this.contains(cls)) { "component " + cls.name + " already added to " + _this }
				val requiredComponents = _this.getClassRequired(cls)
				for (rc in requiredComponents) require(!(!_this.contains(rc) && !containsClass(addedComponent, rc))) {
					("add component " + c + " required " + rc.name + " to "
							+ _this + ", also added " + addedComponent)
				}
			}
			_this.components.addAll(addedComponent)
			for (c in addedComponent) {
				val cls = c.javaClass as Class<out E>
				_this.addAction.event(cls, c)
			}
			for (c in removedComponent) {
				val cls = c.javaClass as Class<out E>
				_this.removeAction.event(cls, c)
			}
			_this.components.removeAll(removedComponent)
		}

		fun <T : E> remove(componentClass: Class<T>): T {
			val component = _this.get(componentClass)
			if (addedComponent.contains(component)) addedComponent.remove(component) else {
				require(!removedComponent.contains(component)) { "$componentClass already remove" }
				removedComponent.add(component)
			}
			return component
		}
	}

	protected open fun getClassRequired(cls: Class<out E>): Iterable<Class<out E>> {
		return IteratorUtil.empty()
	}
}

private inline fun containsClass(iter: Iterable<*>, cls: Class<*>): Boolean {
	return iter.any { cls.isInstance(it) }
}
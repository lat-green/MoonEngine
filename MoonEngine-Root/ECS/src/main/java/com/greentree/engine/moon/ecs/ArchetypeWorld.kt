package com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.filter.Filter
import com.greentree.engine.moon.ecs.filter.builder.world.WorldFilterBuilderBase
import java.io.ObjectInput
import java.io.ObjectOutput

class ArchetypeWorld : World {

	override fun newFilterBuilder(): WorldFilterBuilderBase {
		return root
	}

	private fun getArchetype(cls: Class<out Component>): BranchArchetype {
		return getArchetype(setOf(), cls)
	}

	private fun getArchetype(classes: Set<out Class<out Component>>, cls: Class<out Component>): BranchArchetype {
		return getArchetype(setOf(cls, *(classes.toTypedArray()))) as BranchArchetype
	}

	private fun getArchetype(classes: Set<out Class<out Component>>): Archetype {
		var result: Archetype = root
		val iter = classes.iterator()
		while (iter.hasNext()) {
			val cls = iter.next()
			result = result.child(cls)
		}
		return result
	}

	companion object {

		fun newPrototypeEntity(): PrototypeEntity = ClassSetEntity()
	}

	private abstract inner class Archetype : Filter<ArchetypeEntity>, WorldFilterBuilderBase {

		val activatedEntities: MutableCollection<ArchetypeEntity> = ArrayList()
		val deactivatedEntities: MutableCollection<ArchetypeEntity> = ArrayList()
		val children: MutableMap<Class<out Component>, BranchArchetype> = HashMap()
		abstract val requiredClasses: Set<out Class<out Component>>

		abstract fun parent(componentClass: Class<out Component>): Archetype

		fun child(cls: Class<out Component>): BranchArchetype {
			if (cls in children)
				return children[cls]!!
			val child = BranchArchetype(requiredClasses, cls)
			children[cls] = child
			return child
		}

		fun removeActivated(entity: ArchetypeEntity) {
			activatedEntities.remove(entity)
		}

		fun removeDeactivated(entity: ArchetypeEntity) {
			deactivatedEntities.remove(entity)
		}

		fun addActivated(entity: ArchetypeEntity) {
			activatedEntities.add(entity)
		}

		fun addDeactivated(entity: ArchetypeEntity) {
			deactivatedEntities.add(entity)
		}

		fun clear() {
			activatedEntities.clear()
			deactivatedEntities.clear()
			children.clear()
		}

		override fun <C : Component> require(requiredClass: Class<out C>): WorldFilterBuilderBase {
			return child(requiredClass)
		}

		override fun build(): Filter<out WorldEntity> {
			return this
		}

		override fun isRequired(componentClass: Class<out Component>): Boolean = componentClass in requiredClasses
		override fun isIgnored(componentClass: Class<out Component>): Boolean = false

		override fun iterator(): Iterator<ArchetypeEntity> {
			return sequenceOf(activatedEntities, *(children.values.toTypedArray())).flatten().iterator()
		}

		override fun toString(): String {
			return "Archetype($requiredClasses)"
		}

		val size: Int
			get() = activatedEntities.size + children.values.sumOf { it.size }
	}

	private inner class BranchArchetype(override val requiredClasses: Set<out Class<out Component>>) : Archetype() {

		constructor(requiredClasses: Set<out Class<out Component>>, requiredClass: Class<out Component>) : this(
			setOf(
				requiredClass,
				*(requiredClasses.toTypedArray())
			)
		)

		override fun parent(componentClass: Class<out Component>): Archetype =
			getArchetype(requiredClasses.filter { it != componentClass }.toSet())

		override fun isRequired(requiredClass: Class<out Component>): Boolean {
			return requiredClass in requiredClasses
		}
	}

	private inner class RootArchetype : Archetype() {

		fun newEntity(): WorldEntity {
			val result = ArchetypeEntity(this)
			activatedEntities.add(result)
			return result
		}

		fun newDeactivateEntity(): WorldEntity {
			val result = ArchetypeEntity(this)
			deactivatedEntities.add(result)
			return result
		}

		override val requiredClasses: Set<out Class<out Component>>
			get() = setOf()

		override fun parent(componentClass: Class<out Component>) = throw UnsupportedOperationException()

		override fun isRequired(componentClass: Class<out Component>) = false
	}

	private abstract inner class State {

		abstract fun activate(entity: ArchetypeEntity)
		abstract fun deactivate(entity: ArchetypeEntity)
		abstract fun isActive(entity: ArchetypeEntity): Boolean

		abstract fun add(entity: ArchetypeEntity, component: Component)
		abstract fun remove(entity: ArchetypeEntity, componentClass: Class<out Component>)

		abstract fun delete(entity: ArchetypeEntity)
		abstract fun isDeleted(entity: ArchetypeEntity): Boolean

		open fun clear(entity: ArchetypeEntity) {
			entity.prototype.clear()
			entity.state = DeactiveArchetypesState(root)
		}
	}

	private inner class ActiveArchetypeState(val archetype: Archetype) : State() {

		override fun delete(entity: ArchetypeEntity) {
			archetype.removeActivated(entity)
			entity.prototype.clear()
			entity.state = DeletedState()
		}

		override fun isDeleted(entity: ArchetypeEntity) = false

		override fun activate(entity: ArchetypeEntity) {
			throw UnsupportedOperationException("activate already activated entity $entity")
		}

		override fun isActive(entity: ArchetypeEntity) = true

		override fun add(entity: ArchetypeEntity, component: Component) {
			entity.prototype.add(component)
			archetype.removeActivated(entity)
			val cls = component::class.java
			val newArchetype = archetype.child(cls)
			newArchetype.addActivated(entity)
			entity.state = ActiveArchetypeState(newArchetype)
		}

		override fun remove(entity: ArchetypeEntity, componentClass: Class<out Component>) {
			entity.prototype.remove(componentClass)
			archetype.removeActivated(entity)
			val newArchetype = archetype.parent(componentClass)
			newArchetype.addActivated(entity)
			entity.state = ActiveArchetypeState(newArchetype)
		}

		override fun deactivate(entity: ArchetypeEntity) {
			entity.state = DeactiveArchetypesState(archetype)
		}

		override fun toString(): String {
			return "ActiveArchetypeState($archetype)"
		}
	}

	private inner class DeactiveArchetypesState(val archetype: Archetype) : State() {

		override fun delete(entity: ArchetypeEntity) {
			archetype.removeDeactivated(entity)
			entity.prototype.clear()
			entity.state = DeletedState()
		}

		override fun isDeleted(entity: ArchetypeEntity) = false

		override fun activate(entity: ArchetypeEntity) {
			entity.state = ActiveArchetypeState(archetype)
		}

		override fun isActive(entity: ArchetypeEntity) = false

		override fun deactivate(entity: ArchetypeEntity) {
			throw UnsupportedOperationException("deactivate already deactivated entity $entity")
		}

		override fun add(entity: ArchetypeEntity, component: Component) {
			entity.prototype.add(component)
			archetype.removeDeactivated(entity)
			val cls = component::class.java
			val newArchetype = archetype.child(cls)
			newArchetype.addDeactivated(entity)
			entity.state = DeactiveArchetypesState(newArchetype)
		}

		override fun remove(entity: ArchetypeEntity, componentClass: Class<out Component>) {
			entity.prototype.remove(componentClass)
			archetype.removeDeactivated(entity)
			val newArchetype = archetype.parent(componentClass)
			newArchetype.addDeactivated(entity)
			entity.state = DeactiveArchetypesState(newArchetype)
		}

		override fun toString(): String {
			return "DeactiveArchetypesState($archetype)"
		}
	}

	private inner class DeletedState : State() {

		override fun delete(entity: ArchetypeEntity) {
			throw UnsupportedOperationException("delete already deleted entity $entity")
		}

		override fun isDeleted(entity: ArchetypeEntity) = true

		override fun activate(entity: ArchetypeEntity) {
			throw UnsupportedOperationException("activate already deleted entity $entity")
		}

		override fun isActive(entity: ArchetypeEntity) = false

		override fun add(entity: ArchetypeEntity, component: Component) {
			throw UnsupportedOperationException("add to already deleted entity $entity component: $component")
		}

		override fun remove(entity: ArchetypeEntity, componentClass: Class<out Component>) {
			throw UnsupportedOperationException("remove from already deleted entity $entity componentClass: $componentClass")
		}

		override fun deactivate(entity: ArchetypeEntity) {
			throw UnsupportedOperationException("deactivate already deleted entity $entity")
		}

		override fun clear(entity: ArchetypeEntity) {
			throw UnsupportedOperationException("clear already deleted entity $entity")
		}

		override fun toString(): String {
			return "DeletedState()"
		}
	}

	private inner class ArchetypeEntity private constructor(
		var state: State,
		val prototype: PrototypeEntity,
	) : WorldEntity {

		override fun toString(): String {
			return prototype.toString()
		}

		constructor(archetype: Archetype, prototype: PrototypeEntity = newPrototypeEntity()) : this(
			ActiveArchetypeState(
				archetype
			), prototype
		)

		override fun world() = this@ArchetypeWorld

		override fun delete() {
			return state.delete(this)
		}

		override fun isDeleted(): Boolean {
			return state.isDeleted(this)
		}

		override fun activate() {
			return state.activate(this)
		}

		override fun deactivate() {
			return state.deactivate(this)
		}

		override fun isActive(): Boolean {
			return state.isActive(this)
		}

		override fun add(component: Component) {
			return state.add(this, component)
		}

		override fun copy(): PrototypeEntity {
			return prototype.copy()
		}

		override fun clear() {
			return state.clear(this)
		}

		override fun size(): Int {
			return prototype.size()
		}

		override fun contains(componentClass: Class<out Component>): Boolean {
			return prototype.contains(componentClass)
		}

		override fun contains(component: Component): Boolean {
			return prototype.contains(component)
		}

		override fun <T : Component> get(componentClass: Class<T>): T {
			return prototype[componentClass]
		}

		override fun remove(componentClass: Class<out Component>) {
			return state.remove(this, componentClass)
		}

		override fun save(output: ObjectOutput) {
			TODO("Not yet implemented")
		}

		override fun iterator(): Iterator<Component> {
			return prototype.iterator()
		}
	}

	private val root = RootArchetype()

	override fun save(output: ObjectOutput) {
		TODO("Not yet implemented")
	}

	override fun clear() {
		root.clear()
	}

	override fun newDeactivateEntity(): WorldEntity {
		return root.newDeactivateEntity()
	}

	override fun newEntity(): WorldEntity {
		return root.newEntity()
	}

	override fun loadDeactivateEntity(input: ObjectInput): WorldEntity {
		TODO("Not yet implemented")
	}

	override fun loadEntity(input: ObjectInput): WorldEntity {
		TODO("Not yet implemented")
	}

	override fun size() = root.size

	override fun iterator(): Iterator<out WorldEntity> = root.iterator()
}
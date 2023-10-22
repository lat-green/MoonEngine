package com.greentree.engine.moon.ecs

import com.greentree.engine.moon.kernel.use
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutput

class CollectionWorld(activeInitialCapacity: Int, deactivateInitialCapacity: Int) : World {

	private val activeEntities: MutableCollection<CollectionEntity>
	private val deactivateEntities: MutableCollection<CollectionEntity>

	@JvmOverloads
	constructor(initialCapacity: Int = 128) : this(initialCapacity * (K - 1) / K, initialCapacity / K)

	init {
		activeEntities = HashSet(activeInitialCapacity)
		deactivateEntities = HashSet(deactivateInitialCapacity)
	}

	override fun clear() {
		activeEntities.clear()
		deactivateEntities.clear()
	}

	override fun newDeactivateEntity(): WorldEntity {
		val result = CollectionEntity(newPrototypeEntity())
		result.deactivate()
		return result
	}

	override fun newEntity(): WorldEntity {
		return CollectionEntity(newPrototypeEntity())
	}

	override fun loadDeactivateEntity(input: ObjectInput): WorldEntity {
		val result = CollectionEntity(loadPrototypeEntity(input))
		result.deactivate()
		return result
	}

	override fun loadEntity(input: ObjectInput): WorldEntity {
		return CollectionEntity(loadPrototypeEntity(input))
	}

	override fun size(): Int {
		return activeEntities.size
	}

	override fun save(output: ObjectOutput) {
		save(output, activeEntities)
		save(output, deactivateEntities)
	}

	override fun iterator(): Iterator<WorldEntity> {
		return activeEntities.iterator()
	}

	private fun newPrototypeEntity(): PrototypeEntity {
		return ClassSetEntity()
	}

	private fun loadPrototypeEntity(input: ObjectInput): PrototypeEntity {
		TODO()
	}

	private inner class CollectionEntity(entity: PrototypeEntity) : WorldEntity, ProxyPrototypeEntity(entity) {

		var state: EntityState = ActiveEntityState()

		open abstract inner class EntityState {

			open fun world(): CollectionWorld {
				return this@CollectionWorld
			}

			abstract fun delete()

			open fun isDeleted(): Boolean = false

			abstract fun activate()
			abstract fun isActive(): Boolean
			abstract fun deactivate()
			open fun isDeactivate(): Boolean = !isActive()
		}

		inner class DeletedEntityState : CollectionEntity.EntityState() {

			override fun isDeleted(): Boolean {
				return true
			}

			override fun delete() {
				throw UnsupportedOperationException("already delete")
			}

			override fun activate() {
				throw UnsupportedOperationException("delete")
			}

			override fun isActive(): Boolean {
				return false
			}

			override fun isDeactivate(): Boolean {
				return false
			}

			override fun deactivate() {
				throw UnsupportedOperationException("delete")
			}

			override fun toString(): String {
				return "Deleted"
			}
		}

		inner class ActiveEntityState : CollectionEntity.EntityState() {

			override fun delete() {
				activeEntities.remove(this@CollectionEntity)
				state = DeletedEntityState()
			}

			override fun activate() {
				throw UnsupportedOperationException("already activated")
			}

			override fun isActive(): Boolean {
				return true
			}

			override fun deactivate() {
				activeEntities.remove(this@CollectionEntity)
				deactivateEntities.add(this@CollectionEntity)
				state = DeactivatedEntityState()
			}

			override fun toString(): String {
				return "Actived"
			}
		}

		inner class DeactivatedEntityState : CollectionEntity.EntityState() {

			override fun delete() {
				deactivateEntities.remove(this@CollectionEntity)
				state = DeletedEntityState()
			}

			override fun activate() {
				activeEntities.add(this@CollectionEntity)
				deactivateEntities.remove(this@CollectionEntity)
				state = ActiveEntityState()
			}

			override fun isActive(): Boolean {
				return false
			}

			override fun deactivate() {
				throw UnsupportedOperationException("already deactivate")
			}

			override fun toString(): String {
				return "Deactivated"
			}
		}

		init {
			world().activeEntities.add(this)
		}

		override fun world(): CollectionWorld {
			return state.world()
		}

		override fun delete() {
			return state.delete()
		}

		override fun isDeleted(): Boolean {
			return state.isDeleted()
		}

		override fun activate() {
			return state.activate()
		}

		override fun deactivate() {
			return state.deactivate()
		}

		override fun isDeactivate(): Boolean {
			return state.isDeactivate()
		}

		override fun isActive(): Boolean {
			return state.isActive()
		}

		override fun toString(): String {
			return "CollectionEntity($state, $origin)"
		}
	}

	companion object {

		private const val K = 16

		@JvmStatic
		private fun save(output: ObjectOutput, entities: Collection<WorldEntity>) {
			output.writeInt(entities.size)
			for (e in entities)
				e.save(output)
		}

		@JvmStatic
		fun load(bytes: ByteArray, offset: Int, length: Int): CollectionWorld {
			return use(ByteArrayInputStream(bytes, offset, length)) {
				load(it)
			}
		}

		@JvmStatic
		fun load(bytes: ByteArray): CollectionWorld {
			return use(ByteArrayInputStream(bytes)) {
				load(it)
			}
		}

		@JvmStatic
		fun load(input: InputStream): CollectionWorld {
			if (input is ObjectInput)
				return load(input as ObjectInput)
			return load(ObjectInputStream(input) as ObjectInput)
		}

		@JvmStatic
		fun load(input: ObjectInput): CollectionWorld {
			val result = CollectionWorld()
			repeat(input.readInt()) {
				result.loadEntity(input)
			}
			repeat(input.readInt()) {
				result.loadDeactivateEntity(input)
			}
			return result
		}
	}
}
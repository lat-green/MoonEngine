package com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.filter.builder.world.WorldFilterBuilderBase
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.io.OutputStream

@JvmDefaultWithoutCompatibility
interface World : Iterable<WorldEntity> {

	fun newFilterBuilder(): WorldFilterBuilderBase {
		return WorldFilterBuilderBase.all(this)
	}

	@Throws(IOException::class)
	fun save(output: ObjectOutputStream) {
		return save(output as ObjectOutput)
	}

	@Throws(IOException::class)
	fun save(output: OutputStream) {
		if (output is ObjectOutput)
			return save(output as ObjectOutput)
		val objOutput = ObjectOutputStream(output)
		save(objOutput)
		objOutput.flush()
	}

	@Throws(IOException::class)
	fun save(output: ObjectOutput)

	fun clear()

	fun newDeactivateEntity(): WorldEntity
	fun newEntity(): WorldEntity

	fun loadDeactivateEntity(input: InputStream): WorldEntity {
		if (input is ObjectInput)
			return loadDeactivateEntity(input as ObjectInput)
		return loadDeactivateEntity(ObjectInputStream(input) as ObjectInput)
	}

	fun loadEntity(input: InputStream): WorldEntity {
		if (input is ObjectInput)
			return loadEntity(input as ObjectInput)
		return loadEntity(ObjectInputStream(input) as ObjectInput)
	}

	fun loadDeactivateEntity(input: ObjectInput): WorldEntity
	fun loadEntity(input: ObjectInput): WorldEntity

	fun size(): Int
}

inline fun World.create(noinline function: ComponentLock.() -> Unit): WorldEntity {
	val entity = newEntity()
	entity.lock(function)
	return entity
}
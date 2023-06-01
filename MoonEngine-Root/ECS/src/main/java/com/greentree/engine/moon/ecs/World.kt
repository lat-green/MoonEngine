package com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.filter.FilterBuilder
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.io.OutputStream

@JvmDefaultWithoutCompatibility
interface World : Iterable<WorldEntity> {

	@Throws(IOException::class)
	fun save(output: ObjectOutputStream) {
		return save(output as ObjectOutput)
	}

	@Throws(IOException::class)
	fun save(output: OutputStream) {
		if (output is ObjectOutput)
			return save(output as ObjectOutput)
		var objOutput = ObjectOutputStream(output)
		save(objOutput)
		objOutput.flush()
	}

	@Throws(IOException::class)
	fun save(output: ObjectOutput)

	fun newFilter(): FilterBuilder

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

	@Deprecated(
		"",
		ReplaceWith("e.delete()")
	)
	fun deleteEntity(e: PrototypeEntity) {
		throw UnsupportedOperationException()
	}

	@Deprecated(
		"",
		ReplaceWith("e.delete()")
	)
	fun deleteEntity(e: WorldEntity) {
		if (e.world() === this)
			e.delete()
		else
			throw UnsupportedOperationException()
	}

	@Deprecated(
		"",
		ReplaceWith("!e.isActive()")
	)
	fun isDeactivate(e: WorldEntity): Boolean {
		if (e.world() === this)
			return e.isDeactivate()
		else
			throw UnsupportedOperationException()
	}

	@Deprecated(
		"",
		ReplaceWith("false")
	)
	fun isDeactivate(e: PrototypeEntity): Boolean {
		return false
	}

	@Deprecated(
		"",
		ReplaceWith("false")
	)
	fun isActive(e: PrototypeEntity): Boolean {
		return false
	}

	@Deprecated(
		"",
		ReplaceWith("e.isActivate()")
	)
	fun isActive(e: WorldEntity): Boolean {
		if (e.world() === this)
			return e.isActive()
		else
			throw UnsupportedOperationException()
	}

	@Deprecated(
		"",
		ReplaceWith("e.deactivate()")
	)
	fun deactivate(e: WorldEntity) {
		if (e.world() === this)
			return e.deactivate()
		else
			throw UnsupportedOperationException()
	}

	@Deprecated(
		"",
		ReplaceWith("e.activate()")
	)
	fun active(e: WorldEntity) {
		if (e.world() === this)
			return e.activate()
		else
			throw UnsupportedOperationException()
	}
}
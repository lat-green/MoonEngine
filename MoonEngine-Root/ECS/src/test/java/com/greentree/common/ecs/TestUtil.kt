package com.greentree.common.ecs

import com.greentree.engine.moon.ecs.Entity
import org.junit.jupiter.api.Assertions
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object TestUtil {

	@JvmStatic
	fun assertComponentEquals(a: Entity, b: Entity) {
		for (ac in a) {
			val bc = b[ac.javaClass]
			Assertions.assertEquals(ac, bc)
		}
		for (bc in b) {
			val ac = a[bc.javaClass]
			Assertions.assertEquals(ac, bc)
		}
	}

	@JvmStatic
	fun <T> deser(ser: ByteArray?): T {
		ByteArrayInputStream(ser).use { bout -> ObjectInputStream(bout).use { oout -> return oout.readObject() as T } }
	}

	@JvmStatic
	fun ser(obj: Any): ByteArray {
		ByteArrayOutputStream().use { bout ->
			ObjectOutputStream(bout).use { oout -> oout.writeObject(obj) }
			return bout.toByteArray()
		}
	}
}
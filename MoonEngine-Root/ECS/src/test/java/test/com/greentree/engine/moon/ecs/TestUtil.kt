package test.com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.Entity
import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.use
import com.greentree.engine.moon.ecs.withUse
import org.junit.jupiter.api.Assertions.*
import org.opentest4j.AssertionFailedError
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object TestUtil {

	@JvmStatic
	fun assertComponentEquals(a: Entity, b: Entity) {
		try {
			for (ac in a) {
				assertTrue(b.contains(ac.javaClass)) { "${ac.javaClass} not in $b" }
				val bc = b[ac.javaClass]
				assertEquals(ac, bc)
			}
			for (bc in b) {
				assertTrue(a.contains(bc.javaClass)) { "${bc.javaClass} not in $a" }
				val ac = a[bc.javaClass]
				assertEquals(ac, bc)
			}
		} catch (e: AssertionFailedError) {
			throw AssertionFailedError("$a $b", e)
		}
	}

	@JvmStatic
	fun <T> deser(ser: ByteArray): T {
		ByteArrayInputStream(ser).use { bout -> ObjectInputStream(bout).use { oout -> return oout.readObject() as T } }
	}

	@JvmStatic
	fun ser(obj: Any): ByteArray {
		ByteArrayOutputStream().use { bout ->
			ObjectOutputStream(bout).use { oout -> oout.writeObject(obj) }
			return bout.toByteArray()
		}
	}

	@JvmStatic
	fun ser(obj: World): ByteArray {
		return use(ByteArrayOutputStream()) {
			obj.save(it)
			it.toByteArray()
		}
	}

	@JvmStatic
	fun <T : WorldEntity> deser(ser: ByteArray, world: World): T {
		return withUse(ByteArrayInputStream(ser)) {
			return world.loadEntity(this) as T
		}
	}

	@JvmStatic
	fun ser(entity: Entity): ByteArray {
		return use(ByteArrayOutputStream()) {
			entity.save(it)
			it.toByteArray()
		}
	}
}
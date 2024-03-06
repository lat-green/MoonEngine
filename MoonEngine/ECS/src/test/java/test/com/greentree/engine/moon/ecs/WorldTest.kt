package test.com.greentree.engine.moon.ecs

import com.greentree.commons.tests.IteratorAssertions
import com.greentree.engine.moon.ecs.ClassSetEntity
import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.create
import com.greentree.engine.moon.ecs.pool.ArrayLimitEntityPool
import com.greentree.engine.moon.ecs.pool.EmptyEntityStrategy
import com.greentree.engine.moon.ecs.pool.EntityPoolStrategy
import com.greentree.engine.moon.ecs.pool.PrototypeEntityStrategy
import com.greentree.engine.moon.ecs.pool.StackEntityPool
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import test.com.greentree.engine.moon.ecs.TestUtil.assertComponentEquals
import java.time.Duration
import java.util.function.Consumer
import java.util.stream.Stream

abstract class WorldTest {

	abstract fun runWorld(worldConsumer: Consumer<in World>)

	companion object {

		@JvmStatic
		fun ints(): Stream<Int> {
			return Stream.of(0, 9, 8, -13, 42)
		}

		@JvmStatic
		fun strategies(): Stream<EntityPoolStrategy> {
			val p = ClassSetEntity()
			p.add(AComponent())
			return Stream.of(PrototypeEntityStrategy(p), EmptyEntityStrategy)
		}
	}

	@Nested
	inner class Pools {

		@DisplayName("ArrayLimitEntityPool")
		@Nested
		inner class ArrayLimitEntityPoolTest {

			@MethodSource("test.com.greentree.engine.moon.ecs.WorldTest#strategies")
			@ParameterizedTest
			fun test2(str: EntityPoolStrategy) {
				runWorld { world ->
					ArrayLimitEntityPool(
						world, 3,
						str
					).use { pool ->
						val e1 = pool.get()
						val e2 = pool.get()
						val e3 = pool.get()
						e1!!.delete()
						e2!!.delete()
						e3!!.delete()
						val e4 = pool.get()
						val e5 = pool.get()
						val e6 = pool.get()
						Assertions.assertNull(pool.get())
						Assertions.assertNotNull(e4)
						Assertions.assertNotNull(e5)
						Assertions.assertNotNull(e6)
					}
				}
			}

			@MethodSource("test.com.greentree.engine.moon.ecs.WorldTest#strategies")
			@ParameterizedTest
			fun test4(str: EntityPoolStrategy) {
				runWorld { world ->
					ArrayLimitEntityPool(
						world, 1,
						str
					).use { pool ->
						val e1 = pool.get()
						e1!!.delete()
						val e2 = pool.get()
						Assertions.assertNull(pool.get())
						Assertions.assertNotNull(e2)
					}
				}
			}

			@MethodSource("test.com.greentree.engine.moon.ecs.WorldTest#strategies")
			@ParameterizedTest
			fun testLimit3(str: EntityPoolStrategy) {
				runWorld { world ->
					ArrayLimitEntityPool(
						world, 3,
						str
					).use { pool ->
						Assertions.assertNotNull(pool.get())
						Assertions.assertNotNull(pool.get())
						Assertions.assertNotNull(pool.get())
						Assertions.assertNull(pool.get())
						Assertions.assertNull(pool.get())
						Assertions.assertNull(pool.get())
					}
				}
			}

			@MethodSource("test.com.greentree.engine.moon.ecs.WorldTest#strategies")
			@ParameterizedTest
			fun test3(str: EntityPoolStrategy) {
				runWorld { world ->
					ArrayLimitEntityPool(
						world, 1,
						str
					).use { pool ->
						val e1 = pool.get()
						e1!!.delete()
						val e2 = pool.get()
						Assertions.assertEquals(e1, e2)
					}
				}
			}

			@MethodSource("test.com.greentree.engine.moon.ecs.WorldTest#strategies")
			@ParameterizedTest
			fun testPool(str: EntityPoolStrategy) {
				runWorld { world ->
					ArrayLimitEntityPool(world, 3, str).use { pool ->
						val a = pool.get()
						val b = pool.get()
						assertComponentEquals(a!!, b!!)
					}
				}
			}
		}

		@DisplayName("StackEntityPool")
		@Nested
		inner class StackEntityPoolTest {

			@MethodSource("test.com.greentree.engine.moon.ecs.WorldTest#strategies")
			@ParameterizedTest
			fun testPool(str: EntityPoolStrategy) {
				runWorld { world ->
					StackEntityPool(world, str).use { pool ->
						val a = pool.get()
						val b = pool.get()
						assertComponentEquals(a, b)
					}
				}
			}
		}
	}

	@Nested
	inner class Copy {

		@MethodSource("test.com.greentree.engine.moon.ecs.WorldTest#ints")
		@ParameterizedTest
		fun copyEntity(value: Int) {
			runWorld { world ->
				val entity = world.create {
					add(AComponent(value))
				}
				val c = entity.copy()
				assertComponentEquals(entity, c)
			}
		}

		@MethodSource("test.com.greentree.engine.moon.ecs.WorldTest#ints")
		@ParameterizedTest
		fun copyPrototypeEntity(value: Int) {
			runWorld { world ->
				val entity = world.create {
					add(AComponent(value))
				}
				assertComponentEquals(entity.copy(world), entity)
				val prototype = entity.copy()
				assertComponentEquals(entity, prototype)
				val pc = prototype.copy(world)
				assertComponentEquals(prototype, pc)
			}
		}
	}

	@Nested
	inner class FilterTests {

		@Test
		fun filterOneComponent() {
			runWorld { world ->
				val filter = world.newFilterBuilder().require(AComponent::class.java).build()
				val e = world.create {
					add(AComponent())
				}
				Assertions.assertIterableEquals(listOf(e), filter)
			}
		}

		@Test
		fun filterOneAfterDeleteComponent() {
			runWorld { world ->
				val filter = world.newFilterBuilder().require(AComponent::class.java).build()
				val e = world.create {
					add(AComponent())
				}
				e.delete()
				Assertions.assertIterableEquals(listOf<WorldEntity>(), filter)
			}
		}

		@Test
		fun filterTwoComponent() {
			runWorld { world ->
				val filter =
					world.newFilterBuilder().require(AComponent::class.java).require(BComponent::class.java)
						.build()
				val e = world.create {
					add(AComponent())
					add(BComponent())
				}
				Assertions.assertIterableEquals(listOf(e), filter)
			}
		}

		@Test
		fun filterOneRequiredComponent() {
			runWorld { world ->
				val filter =
					world.newFilterBuilder().require(AComponent::class.java).build()
				val e1 = world.create {
					add(AComponent())
				}
				val e2 = world.create {
					add(BComponent())
				}
				Assertions.assertIterableEquals(listOf(e1), filter)
			}
		}

		@Test
		fun filterOneRequiredOneIgnoreComponent() {
			runWorld { world ->
				var builder = world.newFilterBuilder().require(AComponent::class.java).ignore(BComponent::class.java)
				val filter = builder.build()
				val e1 = world.create {
					add(AComponent())
				}
				val e2 = world.create {
					add(BComponent())
				}
				val e3 = world.create {
					add(CComponent())
				}
				val e4 = world.create {
					add(BComponent())
					add(AComponent())
				}
				val e5 = world.create {
					add(AComponent())
					add(CComponent())
				}
				val e6 = world.create {
					add(BComponent())
					add(CComponent())
				}
				val e67 = world.create {
					add(AComponent())
					add(BComponent())
					add(CComponent())
				}
				IteratorAssertions.assertEqualsAsSet(filter, setOf(e1, e5))
			}
		}

		@Test
		fun filterOneIgnoreComponent() {
			runWorld { world ->
				val filter =
					world.newFilterBuilder().ignore(BComponent::class.java).build()
				val e1 = world.create {
					add(AComponent())
				}
				val e2 = world.create {
					add(BComponent())
				}
				val e3 = world.create {
					add(CComponent())
				}
				val e4 = world.create {
					add(AComponent())
					add(BComponent())
				}
				val e5 = world.create {
					add(AComponent())
					add(CComponent())
				}
				val e6 = world.create {
					add(BComponent())
					add(CComponent())
				}
				val e67 = world.create {
					add(AComponent())
					add(BComponent())
					add(CComponent())
				}
				IteratorAssertions.assertEqualsAsSet(filter, setOf(e1, e3, e5))
			}
		}
	}

	@Nested
	inner class Collections {

		@Test
		fun add_contains() {
			runWorld { world ->
				val e = world.newEntity()
				e.add(AComponent())
				Assertions.assertTrue(AComponent::class.java in e)
			}
		}

		@Test
		fun add_Iterator() {
			runWorld { world ->
				val e = world.newEntity()
				val c = AComponent()
				e.add(c)
				Assertions.assertIterableEquals(listOf(c), e)
			}
		}

		@Test
		fun add_remove_contains() {
			runWorld { world ->
				val e = world.newEntity()
				e.add(AComponent())
				e.remove(AComponent::class.java)
				Assertions.assertFalse(AComponent::class.java in e)
			}
		}
	}

	@Nested
	inner class Actives {

		@Test
		fun deletedEntity() {
			runWorld { world ->
				val e = world.newEntity()
				e.delete()
				Assertions.assertFalse(e.isActive())
				Assertions.assertFalse(e.isDeactivate())
			}
		}

		@Test
		fun deletedDeactiveEntity() {
			runWorld { world ->
				val e = world.newDeactivateEntity()
				e.delete()
				Assertions.assertFalse(e.isActive())
				Assertions.assertFalse(e.isDeactivate())
			}
		}

		@Test
		fun newDeactiveEntity() {
			runWorld { world ->
				val e = world.newDeactivateEntity()
				Assertions.assertFalse(e.isActive())
				Assertions.assertTrue(e.isDeactivate())
			}
		}

		@Test
		fun newEntity() {
			runWorld { world ->
				val e = world.newEntity()
				Assertions.assertTrue(e.isActive())
				Assertions.assertFalse(e.isDeactivate())
			}
		}

		@Test
		fun newEntity_deactivate_activate() {
			runWorld { world ->
				val e = world.newEntity()
				e.deactivate()
				Assertions.assertFalse(e.isActive())
				Assertions.assertTrue(e.isDeactivate())
				e.activate()
				Assertions.assertTrue(e.isActive())
				Assertions.assertFalse(e.isDeactivate())
			}
		}

		@Test
		fun newDeactivateEntity_activate_deactivate() {
			runWorld { world ->
				val e = world.newDeactivateEntity()
				e.activate()
				Assertions.assertTrue(e.isActive())
				Assertions.assertFalse(e.isDeactivate())
				e.deactivate()
				Assertions.assertFalse(e.isActive())
				Assertions.assertTrue(e.isDeactivate())
			}
		}
	}

	@Nested
	internal inner class Timeouts {

		@Test
		fun timeout_isActive() {
			runWorld { world ->
				val e = world.newEntity()
				Assertions.assertTimeout(Duration.ofSeconds(8)) {
					var t = 1000_000
					while(t-- > 0) e.isDeactivate()
					world.clear()
				}
			}
		}

		@Disabled
		@Test
		fun timeout_newDeactiveEntity_deleteEntity() {
			runWorld { world ->
				Assertions.assertTimeout(Duration.ofSeconds(8)) {
					var t = 1000_000
					while(t-- > 0) {
						val e = world.newDeactivateEntity()
						e.delete()
					}
				}
				world.clear()
			}
		}

		@Test
		fun timeout_newEntity() {
			runWorld { world ->
				Assertions.assertTimeout(Duration.ofSeconds(8)) {
					var t = 1000_000
					while(t-- > 0) world.newEntity()
				}
			}
		}

		@Test
		fun timeout_newEntity_chunck() {
			runWorld { world ->
				Assertions.assertTimeout(Duration.ofSeconds(8)) {
					var t = 1000
					while(t-- > 0) {
						var t0 = 1000
						while(t0-- > 0) world.newEntity()
						world.clear()
					}
				}
			}
		}

		@Test
		fun timeout_newEntity_deleteEntity() {
			runWorld { world ->
				Assertions.assertTimeout(Duration.ofSeconds(8)) {
					var t = 1000000
					while(t-- > 0) {
						val e = world.newEntity()
						e.delete()
					}
				}
				world.clear()
			}
		}
	}

	@Test
	fun newDeactiveEntity_deleteEntity() {
		runWorld { world ->
			val e = world.newDeactivateEntity()
			e.delete()
		}
	}

	@Test
	fun newEntity_deactive_deleteEntity() {
		runWorld { world ->
			val e = world.newEntity()
			e.deactivate()
			e.delete()
		}
	}
}

private fun <T, R> T.use(function: (T) -> R): R {
	return function(this)
}
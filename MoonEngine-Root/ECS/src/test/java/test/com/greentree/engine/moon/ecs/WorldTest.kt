package test.com.greentree.engine.moon.ecs

import com.greentree.commons.tests.DisabledIfRunInIDE
import com.greentree.commons.tests.IteratorAssertions
import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.create
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.function.Consumer

abstract class WorldTest {

	abstract fun runWorld(worldConsumer: Consumer<in World>)

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
				var builder = world.newFilterBuilder()
				builder = builder.require(AComponent::class.java)
				builder = builder.ignore(BComponent::class.java)
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
	private inner class Actives {

		@Test
		fun isActive_isDeactive_deleteEntity() {
			runWorld { world ->
				val e = world.newEntity()
				e.delete()
				Assertions.assertFalse(e.isActive())
				Assertions.assertFalse(e.isDeactivate())
			}
		}

		@Test
		fun isActive_isDeactive_newDeactiveEntity() {
			runWorld { world ->
				val e = world.newDeactivateEntity()
				Assertions.assertFalse(e.isActive())
				Assertions.assertTrue(e.isDeactivate())
			}
		}

		@Test
		fun isActive_isDeactive_newEntity() {
			runWorld { world ->
				val e = world.newEntity()
				Assertions.assertTrue(e.isActive())
				Assertions.assertFalse(e.isDeactivate())
			}
		}

		@Test
		fun isActive_isDeactive_newEntity_deactivate_activate() {
			runWorld { world ->
				val e = world.newEntity()
				e.deactivate()
				e.activate()
				Assertions.assertFalse(e.isActive())
				Assertions.assertTrue(e.isDeactivate())
			}
		}
	}

	@DisabledIfRunInIDE
	@Nested
	internal inner class Timeouts {

		@Test
		fun timeout_isActive() {
			runWorld { world ->
				val e = world.newEntity()
				Assertions.assertTimeout(Duration.ofSeconds(2)) {
					var t = 1000000
					while (t-- > 0) e.isDeactivate()
					world.clear()
				}
			}
		}

		@Test
		fun timeout_newDeactiveEntity_deleteEntity() {
			runWorld { world ->
				Assertions.assertTimeout(Duration.ofSeconds(2)) {
					var t = 1000000
					while (t-- > 0) {
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
				Assertions.assertTimeout(Duration.ofSeconds(4)) {
					var t = 1000000
					while (t-- > 0) world.newEntity()
				}
				world.clear()
			}
		}

		@Test
		fun timeout_newEntity_chunck() {
			runWorld { world ->
				Assertions.assertTimeout(Duration.ofSeconds(2)) {
					var t = 1000
					while (t-- > 0) {
						var t0 = 1000
						while (t0-- > 0) world.newEntity()
						world.clear()
					}
				}
			}
		}

		@Test
		fun timeout_newEntity_deleteEntity() {
			runWorld { world ->
				Assertions.assertTimeout(Duration.ofSeconds(2)) {
					var t = 1000000
					while (t-- > 0) {
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
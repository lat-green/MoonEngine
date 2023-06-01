package test.com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.World
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import test.com.greentree.engine.moon.ecs.ACompnent
import java.util.function.Consumer

interface FilterTests {

	@Test
	fun filterOneComponent() {
		runWorld { world ->
			val filter = world.newFilterBuilder().required(ACompnent::class.java).build()
			val e = world.newEntity()
			val c = ACompnent()
			e.add(c)
			Assertions.assertIterableEquals(listOf(e), filter)
			Assertions.assertIterableEquals(listOf(c), filter.map { it.component1() })
		}
	}

	fun runWorld(worldConsumer: Consumer<in World>)
}
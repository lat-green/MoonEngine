package test.com.greentree.engine.moon.assets.reactive

import com.greentree.commons.tests.ExecuteCounter
import com.greentree.engine.moon.assets.reactive.FlattenReactiveAsset
import com.greentree.engine.moon.assets.reactive.LazeReactiveAsset.Companion.toLazy
import com.greentree.engine.moon.assets.reactive.MutableReactiveAsset
import com.greentree.engine.moon.assets.reactive.plusAssign
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LazyReactiveAssetTest {

	@Test
	fun testLazyValue() {
		val m = MutableReactiveAsset("A")
		val asset = m.toLazy()
		ExecuteCounter(0).use {
			asset += { _, _, ->
				it.run()
			}
			m.value = "B"
		}
	}

	@Test
	fun testLazyValueUpdate() {
		val m = MutableReactiveAsset("A")
		val asset = m.toLazy()
		ExecuteCounter(1).use {
			asset += { _, _, ->
				it.run()
			}
			m.value = "B"
			asset.value
		}
	}

	@Test
	fun testValue() {
		val m = MutableReactiveAsset("A")
		val asset = m.toLazy()
		assertEquals(asset.value, "A")
	}
}
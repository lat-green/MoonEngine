package test.com.greentree.engine.moon.assets.reactive

import com.greentree.commons.tests.ExecuteCounter
import com.greentree.engine.moon.assets.reactive.FlattenReactiveAsset
import com.greentree.engine.moon.assets.reactive.MutableReactiveAsset
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FlattenReactiveAssetTest {

	@Test
	fun testValue() {
		val m1 = MutableReactiveAsset("A1")
		val m2 = MutableReactiveAsset("A2")
		val m = MutableReactiveAsset(m1)
		val asset = FlattenReactiveAsset(m)
		assertEquals(asset.value, "A1")
		m1.value = "B1"
		assertEquals(asset.value, "B1")
		m.value = m2
		assertEquals(asset.value, "A2")
		m2.value = "B2"
		assertEquals(asset.value, "B2")
	}

	@Test
	fun checkOnChangeEquals() {
		val m1 = MutableReactiveAsset("A1")
		val m = MutableReactiveAsset(m1)
		val asset = FlattenReactiveAsset(m)
		ExecuteCounter(0).use { c ->
			asset.addListener { _, _ ->
				c.run()
			}
			m1.value = "A1"
		}
	}

	@Test
	fun checkOnChange() {
		val m1 = MutableReactiveAsset("A1")
		val m2 = MutableReactiveAsset("A1")
		val m = MutableReactiveAsset(m1)
		val asset = FlattenReactiveAsset(m)
		ExecuteCounter(1).use {
			asset.addListener { _, _ ->
				it.run()
			}
			m.value = m2
		}
	}
}
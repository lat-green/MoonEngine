package test.com.greentree.engine.moon.assets.provider

import com.greentree.commons.tests.ExecuteCounter
import com.greentree.engine.moon.assets.provider.CacheAssetProvider
import com.greentree.engine.moon.assets.provider.MutableAssetProvider
import com.greentree.engine.moon.assets.provider.context.LastValue
import com.greentree.engine.moon.assets.provider.context.contains
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CacheAssetProviderTest {

	@Test
	fun getValueOnce() {
		ExecuteCounter(1).use {
			val m = MutableAssetProvider("1")
			val asset = CacheAssetProvider(RunOnGetAssetProvider(m, it))
			asset.value()
			asset.value()
		}
	}

	@Test
	fun getValueOnceAfterChange() {
		ExecuteCounter(1).use {
			val m = MutableAssetProvider("1")
			val asset = CacheAssetProvider(RunOnGetAssetProvider(m, it))
			m.value = "2"
			asset.value()
			asset.value()
		}
	}

	@Test
	fun getValueTwiceBeforeAndAfterChange() {
		ExecuteCounter(2).use {
			val m = MutableAssetProvider("1")
			val asset = CacheAssetProvider(RunOnGetAssetProvider(m, it))
			asset.value()
			asset.value()
			m.value = "2"
			asset.value()
			asset.value()
		}
	}

	@Test
	fun hasLastValue() {
		ExecuteCounter(1).use {
			val m = MutableAssetProvider("1")
			var check = false
			val asset = CacheAssetProvider(RunOnGetAssetProvider(m) { ctx ->
				if(check) {
					it.run()
					assertTrue(LastValue in ctx)
				}
			})
			asset.value()
			check = true
			m.value = "2"
			asset.value()
		}
	}

	@Test
	fun getValue() {
		val m = MutableAssetProvider("1")
		val asset = CacheAssetProvider(m)
		assertEquals(asset.value(), "1")
		m.value = "2"
		assertEquals(asset.value(), "2")
	}

	@Test
	fun getCache() {
		val m = MutableAssetProvider("1")
		val asset = CacheAssetProvider(m)

		assertEquals(asset.value, "1")
		m.value = "2"
		assertEquals(asset.value, "1")
	}
}
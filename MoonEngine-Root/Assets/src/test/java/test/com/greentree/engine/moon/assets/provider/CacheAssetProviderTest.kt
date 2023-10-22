package test.com.greentree.engine.moon.assets.provider

import com.greentree.commons.tests.ExecuteCounter
import com.greentree.engine.moon.assets.provider.CacheAssetProvider
import com.greentree.engine.moon.assets.provider.LastValue
import com.greentree.engine.moon.assets.provider.MapAssetProperties
import com.greentree.engine.moon.assets.provider.MutableAssetProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CacheAssetProviderTest {

	@Test
	fun getValueOnce() {
		val ctx = MapAssetProperties()
		ExecuteCounter(1).use {
			val m = MutableAssetProvider("1")
			val asset = CacheAssetProvider(RunOnGetAssetProvider(m, it))
			asset.value(ctx)
			asset.value(ctx)
		}
	}

	@Test
	fun getValueOnceAfterChange() {
		val ctx = MapAssetProperties()
		ExecuteCounter(1).use {
			val m = MutableAssetProvider("1")
			val asset = CacheAssetProvider(RunOnGetAssetProvider(m, it))
			m.value = "2"
			asset.value(ctx)
			asset.value(ctx)
		}
	}

	@Test
	fun getValueTwiceBeforeAndAfterChange() {
		val ctx = MapAssetProperties()
		ExecuteCounter(2).use {
			val m = MutableAssetProvider("1")
			val asset = CacheAssetProvider(RunOnGetAssetProvider(m, it))
			asset.value(ctx)
			asset.value(ctx)
			m.value = "2"
			asset.value(ctx)
			asset.value(ctx)
		}
	}

	@Test
	fun hasLastValue() {
		val ctx = MapAssetProperties()
		ExecuteCounter(1).use {
			val m = MutableAssetProvider("1")
			var check = false
			val asset = CacheAssetProvider(RunOnGetAssetProvider(m) { ctx ->
				if(check) {
					it.run()
					assertTrue(ctx.contains(LastValue::class.java))
				}
			})
			asset.value(ctx)
			check = true
			m.value = "2"
			asset.value(ctx)
		}
	}

	@Test
	fun getValue() {
		val ctx = MapAssetProperties()
		val m = MutableAssetProvider("1")
		val asset = CacheAssetProvider(m)
		assertEquals(asset.value(ctx), "1")
		m.value = "2"
		assertEquals(asset.value(ctx), "2")
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
package test.com.greentree.engine.moon.assets

import com.greentree.commons.tests.aop.AutowiredConfig
import com.greentree.commons.util.time.PointTimer
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.manager.SimpleAssetManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AssetLoadTest {

	private val manager = SimpleAssetManager()

	@Test
	fun AssetManagerCache() {
		val key = ResultAssetKey("55")
		val timer = PointTimer()
		timer.point()
		val asset1 = manager.load<String>(key)
		timer.point()
		val asset2 = manager.load<String>(key)
		timer.point()
		assertEquals(asset1, asset2)
		assertTrue(timer[0] > timer[1])
	}

	companion object {

		private val SLEEP = 5L
	}
}
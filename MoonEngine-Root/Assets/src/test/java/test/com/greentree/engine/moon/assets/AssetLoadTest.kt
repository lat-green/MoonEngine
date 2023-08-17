package test.com.greentree.engine.moon.assets

import com.greentree.commons.util.time.PointTimer
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ConstAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.BaseAssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Thread.*

class AssetLoadTest {

	private val manager = BaseAssetManager()

	init {
		manager.addSerializator(object : AssetSerializator<String> {
			override fun load(manager: AssetManager, key: AssetKey): Asset<String>? {
				if(key is StringAssetKey) {
					sleep(SLEEP)
					return ConstAsset(key.value)
				}
				return null
			}
		})
	}

	@Test
	fun AssetManagerCache() {
		val key = StringAssetKey("55")
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
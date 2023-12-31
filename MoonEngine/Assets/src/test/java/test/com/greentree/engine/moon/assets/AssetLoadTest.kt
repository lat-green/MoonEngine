package test.com.greentree.engine.moon.assets

import com.greentree.commons.util.time.PointTimer
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.ConstAssetProvider
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.manager.BaseAssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Thread.*

class AssetLoadTest {

	private val manager = BaseAssetManager()

	init {
		manager.addSerializator(object : AssetSerializator<String> {
			override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<String> {
				if(key is StringAssetKey) {
					sleep(SLEEP)
					return ConstAssetProvider(key.value)
				}
				throw NotSupportedKeyType
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
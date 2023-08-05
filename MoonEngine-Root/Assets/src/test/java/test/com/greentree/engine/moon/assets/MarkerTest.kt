package test.com.greentree.engine.moon.assets

import com.greentree.engine.moon.assets.serializator.manager.BaseAssetManager
import com.greentree.engine.moon.assets.serializator.manager.MutableAssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MarkerTest {

	private lateinit var manager: MutableAssetManager

	@BeforeEach
	fun setup() {
		manager = BaseAssetManager()
		manager.addGenerator { OneKeyTypeLoad(it) }
		manager.addSerializator(SleepStringAssetSerializator(0L))
	}

	@Test
	fun duplicateKeyType() {
		val key1 = StringAssetKey("1")
		val key2 = StringAssetKey("2")
		val asset1 = manager.load<String>(key1)
		val asset2 = manager.load<String>(key2)
		Assertions.assertEquals(asset1.value, "1")
		Assertions.assertEquals(asset2.value, "2")
	}
}
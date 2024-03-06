package test.com.greentree.engine.moon.assets

import com.greentree.commons.data.resource.location.ClassLoaderResourceLocation
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.tests.MaxExecuteCounter
import com.greentree.commons.tests.aop.AutowiredArgument
import com.greentree.commons.tests.aop.AutowiredConfig
import com.greentree.commons.tests.aop.AutowiredTest
import com.greentree.engine.moon.assets.asset.getValue
import com.greentree.engine.moon.assets.exception.NotValidSource
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.DefaultAssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.loadAsset
import com.greentree.engine.moon.assets.manager.AssetManager
import com.greentree.engine.moon.assets.manager.MutableAssetManager
import com.greentree.engine.moon.assets.serializator.addSerializator
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import test.com.greentree.engine.moon.assets.loader.ResourceToString
import test.com.greentree.engine.moon.assets.loader.StringToInt

@AutowiredConfig(TestConfig::class)
class AssetManagerTest {

	@AutowiredTest
	fun testSimpleAssetSerializator(manager: MutableAssetManager, @AutowiredArgument(tags = ["integer"]) str: String) {
		manager.addSerializator(StringToInt)
		val key = ResultAssetKey(str)
		val asset by manager.loadAsset<Int>(key)
		assertEquals(asset, str.toInt())
	}

	@AutowiredTest
	fun testLoadResourceAssetKey(manager: MutableAssetManager) {
		val location = ClassLoaderResourceLocation()
		manager.addSerializator(ResourceToString)
		manager.addResourceLocation(location)
		val key = ResourceAssetKey("test.txt")
		val asset by manager.loadAsset<String>(key)
		assertEquals(asset, "Hello")
	}

	@AutowiredTest
	fun testLoadResultAssetKey(manager: AssetManager, @AutowiredArgument(tags = ["data"]) any: Any) {
		val key = ResultAssetKey(any)
		val asset by manager.loadAsset(any::class, key)
		assertEquals(asset, any)
	}

	@AutowiredTest
	fun testFailLoad(manager: MutableAssetManager) {
		manager.addSerializator(StringToInt)
		val key = mock(AssetKey::class.java)
		assertThrows(RuntimeException::class.java) {
			manager.loadAsset(Int::class, key)
		}
	}

	@AutowiredTest
	fun testLoadFirstOfDefaultAssetKey(
		manager: AssetManager,
		@AutowiredArgument(tags = ["data"]) result: Any,
	) {
		val key = DefaultAssetKey(
			ResultAssetKey(result),
			mock(AssetKey::class.java)
		)
		val asset by manager.loadAsset(result::class, key)
		assertEquals(asset, result)
	}

	@AutowiredTest
	fun testSecondOfLoadDefaultAssetKey(
		manager: AssetManager,
		@AutowiredArgument(tags = ["data"]) result: Any,
	) {
		val key = DefaultAssetKey(
			mock(AssetKey::class.java),
			ResultAssetKey(result)
		)
		val asset by manager.loadAsset(result::class, key)
		assertEquals(asset, result)
	}

	@AutowiredTest
	fun testCache(manager: MutableAssetManager, @AutowiredArgument(tags = ["data"]) any: Any) {
		MaxExecuteCounter(1).use {
			manager.addLoader(object : AssetLoader {
				override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
					it.run()
					throw NotValidSource
				}
			})
			val key = ResultAssetKey(any)
			val asset1 by manager.loadAsset(any::class, key)
			val asset2 by manager.loadAsset(any::class, key)
			assertEquals(asset1, asset2)
		}
	}
}
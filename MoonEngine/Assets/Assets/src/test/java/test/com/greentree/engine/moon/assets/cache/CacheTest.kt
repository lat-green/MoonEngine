package test.com.greentree.engine.moon.assets.cache

import com.greentree.commons.tests.aop.AutowiredConfig
import com.greentree.commons.tests.aop.AutowiredTest
import com.greentree.engine.moon.assets.cache.CacheFactory
import org.junit.jupiter.api.Assertions.*

@AutowiredConfig(CacheTestConfig::class)
class CacheTest {

	@AutowiredTest
	fun test1(factory: CacheFactory) {
		val cache = factory.newCache<String, String>()
		val key = "key"
		val text = "text"
		val text2 = "text2"
		assertNull(cache[key])
		assertEquals(cache.set(key) { text }, text)
		assertEquals(cache[key], text)
		assertEquals(cache.set(key) { text2 }, text)
	}
}
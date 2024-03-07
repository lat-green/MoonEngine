package test.com.greentree.engine.moon.assets.cache

import com.greentree.commons.tests.aop.AutowiredProvider
import com.greentree.engine.moon.assets.cache.HashMapCacheFactory
import com.greentree.engine.moon.assets.cache.WeakHashMapCacheFactory

class CacheTestConfig {

	@AutowiredProvider
	fun weakHashMapCacheFactory() = WeakHashMapCacheFactory

	@AutowiredProvider
	fun hashMapCacheFactory() = HashMapCacheFactory
}

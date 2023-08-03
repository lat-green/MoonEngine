package test.com.greentree.engine.moon.assets.asset

import com.greentree.engine.moon.assets.asset.CacheAsset
import com.greentree.engine.moon.assets.asset.ConstAsset
import com.greentree.engine.moon.assets.asset.MutableAsset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.isCache
import com.greentree.engine.moon.assets.asset.isConst
import com.greentree.engine.moon.assets.asset.map
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CacheAssetTest {

	private companion object {

		const val VALUE1 = "1"
		const val VALUE2 = "2"
	}

	@Test
	fun newCacheAssetOfCacheAsset() {
		val asset = MutableAsset(VALUE1)
		assertTrue(asset.isCache)
		assertFalse(asset.isConst)
		val cache = CacheAsset.newAsset(asset)
		assertFalse(cache is CacheAsset)
	}

	@Test
	fun newCacheAssetOfConstAsset() {
		val asset = ConstAsset(VALUE1)
		assertTrue(asset.isCache)
		assertTrue(asset.isConst)
		val cache = CacheAsset.newAsset(asset)
		assertFalse(cache is CacheAsset)
	}

	@Test
	fun newCacheAssetOfNotCacheAsset() {
		val asset = MutableAsset(VALUE1).map(Identity())
		assertFalse(asset.isCache)
		assertFalse(asset.isConst)
		val cache = CacheAsset.newAsset(asset)
		assertTrue(cache is CacheAsset)
	}

	@Test
	fun update1() {
		val asset = MutableAsset(VALUE1)
		val cache = CacheAsset.newAsset(asset.map(Identity()))
		assertEquals(asset.value, cache.value)
		assertEquals(asset.lastModified, cache.lastModified)
		asset.value = VALUE2
		assertNotEquals(asset.lastModified, cache.lastModified)
		assertEquals(asset.value, cache.value)
		assertEquals(asset.lastModified, cache.lastModified)
	}
}

private class Identity<T : Any> : Value1Function<T, T> {

	override fun apply(value: T) = value
}

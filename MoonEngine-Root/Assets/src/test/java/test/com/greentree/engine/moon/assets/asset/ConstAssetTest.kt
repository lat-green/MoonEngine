package test.com.greentree.engine.moon.assets.asset

import com.greentree.engine.moon.assets.asset.ConstAsset
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConstAssetTest {

	companion object {

		private const val VALUE = "55"
	}

	private val asset = ConstAsset(VALUE)

	@Test
	fun isConst() {
		assertTrue(asset.isConst())
	}

	@Test
	fun isCache() {
		assertTrue(asset.isCache())
	}

	@Test
	fun isValid() {
		assertTrue(asset.isValid())
	}

	@Test
	fun getValue() {
		assertEquals(asset.value, VALUE)
	}

	@Test
	fun getLastModified() {
		asset.lastModified
	}
}
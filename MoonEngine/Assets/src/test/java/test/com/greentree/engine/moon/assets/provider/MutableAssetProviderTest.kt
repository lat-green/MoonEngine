package test.com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.MutableAssetProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MutableAssetProviderTest {

	@Test
	fun getValue() {
		val asset = MutableAssetProvider("1")
		assertEquals(asset.value, "1")
	}

	@Test
	fun setValue_getValue() {
		val asset = MutableAssetProvider("1")
		asset.value = "2"
		assertEquals(asset.value, "2")
	}

	@Test
	fun changeLastModifiedOnSetValue() {
		val asset = MutableAssetProvider("1")
		val lastModified = asset.lastModified
		asset.value = "2"
		assertNotEquals(asset.lastModified, lastModified)
	}

	@Test
	fun noChangeLastModifiedOnEqualsSetValue() {
		val asset = MutableAssetProvider("1")
		val lastModified = asset.lastModified
		asset.value = "1"
		assertEquals(asset.lastModified, lastModified)
	}

	@Test
	fun changeHandlers() {
		val asset = MutableAssetProvider("1")
		assertEquals(asset.changeHandlers.toList(), listOf(asset))
	}
}
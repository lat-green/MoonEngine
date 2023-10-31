package test.com.greentree.engine.moon.assets.bundle

import com.greentree.commons.data.FileUtil
import com.greentree.commons.data.resource.location.RootFileResourceLocation
import com.greentree.engine.moon.assets.bundle.MetaResourceLocationAssetBundle
import com.greentree.engine.moon.assets.bundle.load
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.nio.file.Files

const val TEXT = "TEXT"

class MetaResourceLocationAssetBundleTest {

	lateinit var temp: File

	@BeforeEach
	fun initTemp() {
		temp = Files.createTempDirectory("meta-test").toFile()
		val meta = File(temp, "player.name")
		ObjectOutputStream(FileOutputStream(meta)).use {
			it.writeObject(TEXT)
		}
	}

	@AfterEach
	fun deleteTemp() {
		FileUtil.delete(temp)
	}

	@Test
	fun load() {
		val loc = RootFileResourceLocation(temp)
		val meta = MetaResourceLocationAssetBundle(loc)
		val name = meta.load<String>("player.name")
		Assertions.assertEquals(name, TEXT)
	}

	@Test
	fun hasTrue() {
		val loc = RootFileResourceLocation(temp)
		val meta = MetaResourceLocationAssetBundle(loc)
		assertTrue(meta.has("player.name"))
	}

	@Test
	fun hasFalse() {
		val loc = RootFileResourceLocation(temp)
		val meta = MetaResourceLocationAssetBundle(loc)
		assertFalse(meta.has("player.title"))
	}

}

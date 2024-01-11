package com.greentree.engine.moon.cooker

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo
import java.io.InputStream

interface AssetImporter {

	fun importAsset(ctx: Context, asset: AssetInfo): ImportAssetInfo?

	interface Context {

		fun readFile(name: String): InputStream

		@Throws(ClassNotFoundException::class)
		fun <T> findClass(baseClass: Class<T>, name: String): Class<out T>

		fun ask(text: String): Boolean
	}
}

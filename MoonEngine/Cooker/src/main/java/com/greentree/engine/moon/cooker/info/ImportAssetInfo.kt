package com.greentree.engine.moon.cooker.info

import java.io.InputStream

interface ImportAssetInfo {

	fun open(): InputStream

	val isPrimary: Boolean
	val dependencies: Iterable<String>
}

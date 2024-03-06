package com.greentree.engine.moon.cooker.info

import java.io.InputStream

interface ImportAssetInfo {

	val dependencies: Iterable<String>

	fun open(): InputStream
}

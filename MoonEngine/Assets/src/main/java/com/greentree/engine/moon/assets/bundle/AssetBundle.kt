package com.greentree.engine.moon.assets.bundle

import java.io.InputStream

interface AssetBundle {

	fun open(): InputStream

	fun length(): Long

	fun lastModified(): Long
}

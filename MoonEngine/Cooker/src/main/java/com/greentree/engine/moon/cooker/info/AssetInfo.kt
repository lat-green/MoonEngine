package com.greentree.engine.moon.cooker.info

import java.io.InputStream

interface AssetInfo {

	val fileType: String

	fun open(): InputStream
}
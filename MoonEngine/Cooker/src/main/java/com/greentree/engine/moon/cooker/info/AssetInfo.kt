package com.greentree.engine.moon.cooker.info

import com.greentree.commons.data.FileUtil
import java.io.InputStream

interface AssetInfo {

	val fileType: String
		get() = FileUtil.getType(fileName)
	val fileName: String

	fun open(): InputStream
}
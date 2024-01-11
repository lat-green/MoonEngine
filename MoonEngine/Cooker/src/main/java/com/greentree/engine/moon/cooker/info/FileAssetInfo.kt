package com.greentree.engine.moon.cooker.info

import com.greentree.commons.data.FileUtil
import java.io.File
import java.io.FileInputStream

data class FileAssetInfo(
	val file: File,
) : AssetInfo {

	override val fileType: String
		get() = FileUtil.getType(file)

	override fun open() = FileInputStream(file)
}
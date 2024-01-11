package com.greentree.engine.moon.cooker.info

import com.greentree.commons.data.FileUtil
import java.io.File
import java.io.FileInputStream

fun FileAssetInfo(
	root: File,
	file: File,
) = FileAssetInfo(
	file,
	FileUtil.getLocalPath(root, file).replace('\\', '/')
)

data class FileAssetInfo(
	val file: File,
	override val fileName: String,
) : AssetInfo {

	override fun open() = FileInputStream(file)
}
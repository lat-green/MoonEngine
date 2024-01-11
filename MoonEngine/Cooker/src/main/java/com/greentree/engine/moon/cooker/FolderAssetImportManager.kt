package com.greentree.engine.moon.cooker

import com.greentree.commons.data.FileUtil
import com.greentree.engine.moon.cooker.filter.AssetImportFilter
import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.FileAssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfoImpl
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class FolderAssetImportManager(
	private val inputFolder: File,
	private val outputFolder: File,
) : AssetImportManager {

	private val assets = mutableListOf<Pair<ImportAssetInfo, File>>()
	private val assetImporters = mutableListOf<AssetImporter>()
	private val assetImportFilters = mutableListOf<AssetImportFilter>()

	override fun addImporter(importer: AssetImporter) {
		assetImporters.add(importer)
	}

	override fun addFilter(filter: AssetImportFilter) {
		assetImportFilters.add(filter)
	}

	private inner class RootChain(val context: AssetImporter.Context) : AssetImportFilter.Chain {

		override fun doFilter(asset: AssetInfo): ImportAssetInfo {
			for(assetImporter in assetImporters) {
				val importedAsset = assetImporter.importAsset(context, asset)
				if(importedAsset != null)
					return importedAsset
			}
			return ImportAssetInfoImpl(asset, false)
		}
	}

	fun importAsset(context: AssetImporter.Context, file: File): ImportAssetInfo? {
		var chain: AssetImportFilter.Chain = RootChain(context)
		for(filter in assetImportFilters)
			chain = WrapAssetImportFilterChain(filter, chain)
		val asset = FileAssetInfo(inputFolder, file.absoluteFile)
		val importedAsset = chain.doFilter(asset) ?: return null
		val outputFile = File(outputFolder, FileUtil.getLocalPath(inputFolder, file))
		assets.add(importedAsset to outputFile)
		return importedAsset
	}

	fun build() {
		val toBuild = mutableSetOf<Pair<ImportAssetInfo, File>>()
		for(p in assets)
			if(p.first.isPrimary)
				toBuild.add(p)
		var dependencies: Collection<Pair<ImportAssetInfo, File>> = toBuild
		do {
			dependencies = dependencies.flatMap { it.first.dependencies }.map { assets.found(it) }
		} while(toBuild.addAll(dependencies))
		for((importedAsset, outputFile) in toBuild) {
			outputFile.parentFile.mkdirs()
			outputFile.createNewFile()
			importedAsset.open().use { input ->
				FileOutputStream(outputFile).use { output ->
					input.mytransferTo(output)
				}
			}
		}
	}
}

private fun Iterable<Pair<ImportAssetInfo, File>>.found(name: String): Pair<ImportAssetInfo, File> {
	val name = name.replace('\\', '/')
	for(p in this) {
		val file = p.second
		if(file.absolutePath.replace('\\', '/').endsWith(name))
			return p
	}
	throw FileNotFoundException("$name in ${map { it.second }}")
}

fun InputStream.mytransferTo(out: OutputStream): Long {
	Objects.requireNonNull(out, "out")
	var transferred: Long = 0
	val buffer = ByteArray(16384)
	var read: Int
	while((this.read(buffer, 0, 16384).also {
			read = it
		}) >= 0) {
		out.write(buffer, 0, read)
		if(transferred < Long.MAX_VALUE) {
			transferred = try {
				Math.addExact(transferred, read.toLong())
			} catch(ignore: ArithmeticException) {
				Long.MAX_VALUE
			}
		}
	}
	return transferred
}
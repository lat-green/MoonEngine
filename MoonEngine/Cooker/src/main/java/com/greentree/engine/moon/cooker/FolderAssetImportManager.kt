package com.greentree.engine.moon.cooker

import com.greentree.commons.data.FileUtil
import com.greentree.engine.moon.cooker.filter.AssetImportFilter
import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.FileAssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfoImpl
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class FolderAssetImportManager(
	private val inputFolder: File,
	private val outputFolder: File,
) : AssetImportManager {

	private val assetImporters = mutableListOf<AssetImporter>()
	private val assetImportFilters = mutableListOf<AssetImportFilter>()

	override fun addImporter(importer: AssetImporter) {
		assetImporters.add(importer)
	}

	override fun addFilter(filter: AssetImportFilter) {
		assetImportFilters.add(filter)
	}

	private inner class RootChain(val context: AssetImporter.Context) : AssetImportFilter.ChainAndChainIsPrimary {

		override fun doFilter(asset: AssetInfo): ImportAssetInfo {
			for(assetImporter in assetImporters) {
				val importedAsset = assetImporter.importAsset(context, asset)
				if(importedAsset != null)
					return importedAsset
			}
			return ImportAssetInfoImpl(asset)
		}

		override fun isPrimary(asset: AssetInfo) = false
	}

	private fun importAsset(
		chain: AssetImportFilter.ChainAndChainIsPrimary,
		asset: AssetInfo,
		file: File,
	): BuildAssetInfo? {
		val info = chain.doFilter(asset)
		if(info != null) {
			val outputFile = File(outputFolder, FileUtil.getLocalPath(inputFolder, file))
			return BuildAssetInfo(info, outputFile)
		}
		return null
	}

	fun importAssets(context: AssetImporter.Context, assets: Iterable<File>): Iterable<ImportAssetInfo> {
		var chain: AssetImportFilter.ChainAndChainIsPrimary = RootChain(context)
		for(filter in assetImportFilters)
			chain = WrapAssetImportFilterChain(filter, chain)
		val toBuild = mutableSetOf<BuildAssetInfo>()
		for(file in assets) {
			val asset = FileAssetInfo(inputFolder, file.absoluteFile)
			if(chain.isPrimary(asset)) {
				importAsset(chain, asset, file)?.let {
					toBuild.add(it)
				}
			}
		}
		var dependencies: Collection<BuildAssetInfo> = toBuild
		do {
			dependencies = dependencies.flatMap { it.info.dependencies }.map {
				toBuild.found(it) ?: found(chain, assets, it)
			}
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
		return toBuild.map { it.info }
	}

	private fun found(
		chain: AssetImportFilter.ChainAndChainIsPrimary,
		files: Iterable<File>,
		name: String,
	): BuildAssetInfo {
		val name = name.replace('\\', '/')
		for(file in files)
			if(file.absolutePath.replace('\\', '/').endsWith(name)) {
				val asset = FileAssetInfo(inputFolder, file.absoluteFile)
				importAsset(chain, asset, file)?.let {
					return it
				}
			}
		throw IllegalArgumentException("not found dependency $name in $files")
	}
}

private data class BuildAssetInfo(
	val info: ImportAssetInfo,
	val outputFile: File,
)

private fun Iterable<BuildAssetInfo>.found(name: String): BuildAssetInfo? {
	val name = name.replace('\\', '/')
	for(p in this) {
		val file = p.outputFile
		if(file.absolutePath.replace('\\', '/').endsWith(name))
			return p
	}
	return null
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
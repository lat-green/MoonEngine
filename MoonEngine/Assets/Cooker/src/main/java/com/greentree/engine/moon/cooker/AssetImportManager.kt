package com.greentree.engine.moon.cooker

import com.greentree.engine.moon.cooker.filter.AssetImportFilter

interface AssetImportManager {

	fun addImporter(importer: AssetImporter)

	fun addFilter(filter: AssetImportFilter)
}
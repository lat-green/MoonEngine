package com.greentree.engine.moon.assets

import com.greentree.engine.moon.assets.PersonAssetImporter.importForm

object ImportPerson {

	@JvmStatic
	fun main(args: Array<String>) {
		val person = importForm(ConsoleAsserImporterContext)
		println(person)
	}
}

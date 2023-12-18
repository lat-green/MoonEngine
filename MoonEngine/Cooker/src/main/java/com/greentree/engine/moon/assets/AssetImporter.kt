package com.greentree.engine.moon.assets

interface AssetImporter<T> {

	fun importForm(context: Context): T

	interface Context {

		fun <R> askForm(form: FormBuilder.() -> R): R

		fun ask(text: String): Boolean
	}
}


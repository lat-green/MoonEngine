package com.greentree.engine.moon.assets

import java.util.*

object ConsoleAsserImporterContext : AssetImporter.Context {

	private val sc = Scanner(System.`in`)

	override fun <R> askForm(form: FormBuilder.() -> R) = object : FormBuilder {
		override fun inputString(name: String) = ref {
			print("$name:")
			val text = sc.nextLine()
			text
		}

		override fun inputInt(name: String) = ref {
			print("$name:")
			val text = sc.nextLine()
			text.toInt()
		}

		override fun inputRadio(name: String, values: Iterable<String>): Ref<String> = ref {
			var result: String
			do {
				print("$name one of (${values.map { it.toString() }.reduce { a, b -> "$a, $b" }}):")
				result = sc.nextLine()
				if(result in values)
					break
			} while(true)
			result
		}

		override fun inputBoolean(name: String) = ref {
			println("$name\n[y/n]:")
			sc.nextLine().lowercase(Locale.getDefault()) == "y"
		}
	}.form()

	override fun ask(text: String): Boolean {
		println("$text\n[y/n]:")
		return sc.nextLine().lowercase(Locale.getDefault()) == "y"
	}
}

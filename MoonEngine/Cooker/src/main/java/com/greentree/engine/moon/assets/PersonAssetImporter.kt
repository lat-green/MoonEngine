package com.greentree.engine.moon.assets

object PersonAssetImporter : AssetImporter<Person> {

	override fun importForm(context: AssetImporter.Context) = context.askForm {
		val name by inputString("name")
		val role by inputEnum<Role>("role")
		val age by inputInt("age")
		Person(name, role, age)
	}
}
package com.greentree.engine.moon.assets

data class Person(
	val name: String,
	val role: Role,
	val age: Int,
)

enum class Role {
	USER, ADMIN;

	companion object
}

package com.greentree.engine.moon.assets.asset

interface AssetCharacteristics {

	val lastModified: Long

	fun isValid(): Boolean = true
	fun isConst(): Boolean = false
}

fun AssetCharacteristics.isChange(lastRead: Long) = isChange(lastModified, lastRead)
fun isChange(lastModified: Long, lastRead: Long) = lastRead < lastModified

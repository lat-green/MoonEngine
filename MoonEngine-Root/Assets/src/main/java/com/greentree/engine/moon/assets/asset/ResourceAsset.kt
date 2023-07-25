package com.greentree.engine.moon.assets.asset

import com.greentree.commons.data.resource.Resource

class ResourceAsset(override val value: Resource) : Asset<Resource> {

	override val lastModified: Long
		get() = value.lastModified()

	override fun isCache() = false
}
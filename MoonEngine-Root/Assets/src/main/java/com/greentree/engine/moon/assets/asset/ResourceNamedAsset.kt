package com.greentree.engine.moon.assets.asset

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.location.ResourceLocation
import java.lang.Long.*

class ResourceNamedAsset(private val resources: ResourceLocation, private val name: Asset<String>) : Asset<Resource> {

	override val value: Resource
		get() = resources.getResource(name.value)
	override val lastModified
		get() = max(name.lastModified, value.lastModified())

	override fun isCache() = true
}
package com.greentree.engine.moon.assets.asset

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.location.ResourceLocation
import java.lang.Long.*

interface ResourceAsset : Asset<Resource> {

	override val lastModified: Long
		get() = value.lastModified()
}

data class ResourceAssetImpl constructor(override val value: Resource) : ResourceAsset {
	constructor(resources: ResourceLocation, name: String) : this(resources.getResource(name))

	override fun toString(): String {
		return "ResourceAsset[$value]"
	}
}

data class ResourceNamedAsset private constructor(
	private val resources: ResourceLocation,
	private val name: Asset<String>,
) : Asset<Resource> {

	override val value: Resource
		get() = resources.getResource(name.value)
	override val lastModified
		get() = max(name.lastModified, resources.getResource(name.value).lastModified())

	override fun isValid() = name.isValid() && resources.isExist(name.value)

	override fun toString(): String {
		return "ResourceNamedAsset($name)"
	}

	companion object {

		fun newAsset(resources: ResourceLocation, name: Asset<String>): Asset<Resource> {
			if(name.isConst)
				return newResourceAsset(resources.getResource(name.value))
			return ResourceNamedAsset(resources, name)
		}
	}
}

fun newResourceAsset(resources: ResourceLocation, name: Asset<String>): Asset<Resource> =
	ResourceNamedAsset.newAsset(resources, name)

fun newResourceAsset(resources: ResourceLocation, name: String): Asset<Resource> = ResourceAssetImpl(resources, name)
fun newResourceAsset(resource: Resource): Asset<Resource> = ResourceAssetImpl(resource)

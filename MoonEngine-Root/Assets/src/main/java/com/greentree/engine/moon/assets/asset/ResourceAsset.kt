package com.greentree.engine.moon.assets.asset

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.location.ResourceLocation
import java.lang.Long.*

data class ResourceAssetImpl constructor(override val value: Resource) : Asset<Resource> {
	constructor(resources: ResourceLocation, name: String) : this(resources.getResource(name))

	override fun isValid() = value.exists()

	override val lastModified
		get() = value.lastModified()

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
	private val _lastModified
		get() =
			if(name.isValid())
				max(name.lastModified, value.lastModified())
			else
				name.lastModified
	override var lastModified = _lastModified
		private set
		get() {
			field = max(field, _lastModified)
			return field
		}

	override fun isValid() = name.isValid() && resources.isExist(name.value)

	override fun toString(): String {
		return "ResourceNamedAsset($name)"
	}

	companion object {

		fun newAsset(resources: ResourceLocation, name: Asset<String>): Asset<Resource> {
			if(name.isConst())
				return newResourceAsset(resources.getResource(name.value))
			return ResourceNamedAsset(resources, name)
		}
	}
}

fun newResourceAsset(resources: ResourceLocation, name: Asset<String>): Asset<Resource> =
	ResourceNamedAsset.newAsset(resources, name)

fun newResourceAsset(resources: ResourceLocation, name: String): Asset<Resource> = ResourceAssetImpl(resources, name)
fun newResourceAsset(resource: Resource): Asset<Resource> = ResourceAssetImpl(resource)

package com.greentree.engine.moon.assets.asset

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.ResourceNotFoundException
import com.greentree.commons.data.resource.location.ResourceLocation
import java.lang.Long.*

private const val UPDATE_DELTA = 1000L

class ResourceAssetImpl(private val resource: Resource) : Asset<Resource> {
	constructor(resources: ResourceLocation, name: String) : this(resources.getResource(name))

	override val value: Resource
		get() {
			if(!resource.exists())
				throw ResourceNotFoundException("$resource")
			return resource
		}

	override fun isValid() = resource.exists()

	override val lastModified
		get() = resource.lastModified()

	override fun toString(): String {
		return "ResourceAsset[$resource]"
	}
}

data class ResourceNamedAsset private constructor(
	private val resources: ResourceLocation,
	private val name: Asset<String>,
) : Asset<Resource> {

	private val resource: Resource
		get() = resources.getResource(name.value)
	override val value: Resource
		get() {
			val resource = resource
			if(!resource.exists())
				throw ResourceNotFoundException("$resource")
			return resource
		}
	private var lastModifiedUpdate = System.currentTimeMillis()
	private val _lastModified
		get() =
			if(name.isValid())
				max(name.lastModified, value.lastModified())
			else
				name.lastModified
	override var lastModified = _lastModified
		//TODO fix bug with <init> get resource
		private set
		get() {
			val currentTimeMillis = System.currentTimeMillis()
			if(lastModifiedUpdate > currentTimeMillis) {
				field = max(field, _lastModified)
				lastModifiedUpdate = currentTimeMillis + UPDATE_DELTA
			}
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

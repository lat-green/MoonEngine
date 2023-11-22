package com.greentree.engine.moon.assets.provider

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.ResourceNotFoundException
import com.greentree.commons.data.resource.location.ResourceLocation
import java.lang.Long
import kotlin.String

private const val UPDATE_DELTA = 1000L

class ResourceAssetProviderImpl(private val resource: Resource) : AssetProvider<Resource> {
	constructor(resources: ResourceLocation, name: String) : this(resources.getResource(name))

	override fun isValid() = resource.exists()

	override fun value(ctx: AssetContext): Resource {
		if(!resource.exists())
			throw ResourceNotFoundException("$resource")
		return resource
	}

	override val lastModified
		get() = resource.lastModified()

	override fun toString(): String {
		return "ResourceAsset[$resource]"
	}
}

data class ResourceNamedAssetProvider(
	private val resources: ResourceLocation,
	private val name: AssetProvider<String>,
) : AssetProvider<Resource> {

	private fun resource(ctx: AssetContext): Resource {
		return resources.getResource(name.value(ctx))
	}

	private val _lastModified
		get() =
			if(name.isValid())
				Long.max(name.lastModified, value.lastModified())
			else
				name.lastModified
	private var lastModifiedUpdate = System.currentTimeMillis()

	override fun value(ctx: AssetContext): Resource {
		val resource = resource(ctx)
		if(!resource.exists())
			throw ResourceNotFoundException("$resource")
		return resource
	}

	override var lastModified = _lastModified
		//TODO fix bug with <init> get resource
		private set
		get() {
			val currentTimeMillis = System.currentTimeMillis()
			if(lastModifiedUpdate < currentTimeMillis) {
				lastModifiedUpdate = currentTimeMillis + UPDATE_DELTA
				field = Long.max(field, _lastModified)
			}
			return field
		}

	override fun isValid() = name.isValid() && resources.isExist(name.value)

	override fun toString(): String {
		return "ResourceNamedAsset($name)"
	}
}

fun newResourceAsset(resources: ResourceLocation, name: AssetProvider<String>): AssetProvider<Resource> =
	ResourceNamedAssetProvider(resources, name)

fun newResourceAsset(resources: ResourceLocation, name: String): AssetProvider<Resource> =
	ResourceAssetProviderImpl(resources, name)

fun newResourceAsset(resource: Resource): AssetProvider<Resource> = ResourceAssetProviderImpl(resource)

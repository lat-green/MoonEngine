package com.greentree.engine.moon.assets.provider

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.ResourceNotFoundException
import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.TryNotUpdate
import com.greentree.engine.moon.assets.provider.response.AssetResponse
import com.greentree.engine.moon.assets.provider.response.BaseNotValidWithException
import com.greentree.engine.moon.assets.provider.response.BaseResult
import com.greentree.engine.moon.assets.provider.response.NotValid
import com.greentree.engine.moon.assets.provider.response.ResultResponse
import kotlin.math.max

private const val UPDATE_DELTA = 1000L

class ResourceAssetProvider(private val resource: Resource) : AssetProvider<Resource> {
	constructor(resources: ResourceLocation, name: String) : this(resources.getResource(name))

	override fun value(ctx: AssetRequest) =
		if(!resource.exists())
			BaseNotValidWithException(ResourceNotFoundException("$resource"))
		else
			BaseResult(resource)

	override val lastModified
		get() = if(resource.exists()) resource.lastModified() else 0

	override fun toString(): String {
		return "ResourceAsset[$resource]"
	}
}

data class ResourceNamedAssetProvider(
	private val resources: ResourceLocation,
	private val name: AssetProvider<String>,
) : AssetProvider<Resource> {

	private var lastModifiedUpdate = System.currentTimeMillis()

	override fun value(ctx: AssetRequest): AssetResponse<Resource> {
		return when(val name = name.value(ctx)) {
			is ResultResponse -> {
				val resource = resources.getResource(name.value)
				if(resource.exists())
					BaseResult(resource)
				else
					BaseNotValidWithException(ResourceNotFoundException("$resource"))
			}

			is NotValid -> name
		} as AssetResponse<Resource>
	}

	override val lastModified
		get() = when(val n = name.value(TryNotUpdate)) {
			is ResultResponse -> max(name.lastModified, resources.getResource(n.value).lastModified())
			else -> name.lastModified
		}

	override fun toString(): String {
		return "ResourceNamedAsset($name)"
	}
}

fun newResourceAsset(resources: ResourceLocation, name: AssetProvider<String>): AssetProvider<Resource> =
	ResourceNamedAssetProvider(resources, name)

fun newResourceAsset(resources: ResourceLocation, name: String): AssetProvider<Resource> =
	ResourceAssetProvider(resources, name)

fun newResourceAsset(resource: Resource): AssetProvider<Resource> = ResourceAssetProvider(resource)

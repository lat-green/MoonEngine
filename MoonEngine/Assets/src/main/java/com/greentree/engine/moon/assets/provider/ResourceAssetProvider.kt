package com.greentree.engine.moon.assets.provider

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.TryNotUpdate

private const val UPDATE_DELTA = 1000L

class ResourceAssetProvider(private val resource: Resource) : AssetProvider<Resource>, ChangeHandler {
	constructor(resources: ResourceLocation, name: String) : this(resources.getResource(name))

	override fun value(ctx: AssetRequest) = resource

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequenceOf(this)
	override val lastModified
		get() = if(resource.exists()) resource.lastModified() else 0

	override fun toString(): String {
		return "ResourceAsset[$resource]"
	}
}

data class ResourceNamedAssetProvider(
	private val resources: ResourceLocation,
	private val name: AssetProvider<String>,
) : AssetProvider<Resource>, ChangeHandler {

	private var lastModifiedUpdate = System.currentTimeMillis()

	override fun value(ctx: AssetRequest) = resources.getResource(name.value(ctx))

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			yieldAll(name.changeHandlers)
			yield(this@ResourceNamedAssetProvider)
		}
	override val lastModified
		get() = resources.getResource(name.value(TryNotUpdate)).lastModified()

	override fun toString(): String {
		return "ResourceNamedAsset($name)"
	}
}

fun newResourceAsset(resources: ResourceLocation, name: AssetProvider<String>): AssetProvider<Resource> =
	ResourceNamedAssetProvider(resources, name)

fun newResourceAsset(resources: ResourceLocation, name: String): AssetProvider<Resource> =
	ResourceAssetProvider(resources, name)

fun newResourceAsset(resource: Resource): AssetProvider<Resource> = ResourceAssetProvider(resource)

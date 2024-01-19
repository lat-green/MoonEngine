package com.greentree.engine.moon.assets.provider

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.TryNotUpdate
import kotlin.reflect.KProperty

private const val UPDATE_DELTA = 10000L

class ResourceAssetProvider(private val resource: Resource) : AssetProvider<Resource>, ChangeHandler {
	constructor(resources: ResourceLocation, name: String) : this(resources.getResource(name))

	override fun value(ctx: AssetRequest) = resource

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequenceOf(this)
	override val lastModified by getOnePerDelta(UPDATE_DELTA) {
		if(resource.exists())
			resource.lastModified() else 0
	}

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
	override val lastModified by getOnePerDelta(UPDATE_DELTA) {
		kotlin.runCatching {
			name.value(TryNotUpdate)
		}.mapCatching {
			resources.getResource(it).lastModified()
		}.getOrElse {
			0L
		}
	}

	override fun toString(): String {
		return "ResourceNamedAsset($name)"
	}
}

fun newResourceAsset(resources: ResourceLocation, name: AssetProvider<String>): AssetProvider<Resource> {
	if(name is ConstAssetProvider && resources.isExist(name.value))
		try {
			return newResourceAsset(resources.getResource(name.value))
		} catch(e: Exception) {
		}
	return ResourceNamedAssetProvider(resources, name)
}

fun newResourceAsset(resources: ResourceLocation, name: String): AssetProvider<Resource> =
	ResourceAssetProvider(resources, name)

fun newResourceAsset(resource: Resource): AssetProvider<Resource> = ResourceAssetProvider(resource)

private class GetPerDelta<R>(
	val updateDelta: Long,
	val block: () -> R,
) {

	private var nextGetTime = System.currentTimeMillis() + updateDelta
	private var value = block()

	operator fun getValue(thisRef: Any?, property: KProperty<*>): R {
		val currentTime = System.currentTimeMillis()
		if(nextGetTime < currentTime) {
			nextGetTime = currentTime + updateDelta
			value = block()
		}
		return value
	}
}

private fun <R> getOnePerDelta(updateDelta: Long, block: () -> R) = GetPerDelta(updateDelta, block)

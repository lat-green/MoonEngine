package com.greentree.engine.moon.assets.bundle

import com.greentree.commons.data.resource.Resource
import com.greentree.engine.moon.assets.bundle.manager.AssetBundleManager

data class ResourceAssetBundle(
	val resource: Resource,
) : AssetBundle {

	override fun open() = resource.open()

	override fun length() = resource.length()

	override fun lastModified() = resource.lastModified()
}

fun AssetBundleManager.load(resource: Resource) = load(ResourceAssetBundle(resource))
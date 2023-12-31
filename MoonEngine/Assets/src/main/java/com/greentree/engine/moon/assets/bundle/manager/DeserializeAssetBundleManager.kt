package com.greentree.engine.moon.assets.bundle.manager

import com.greentree.engine.moon.assets.bundle.deserializator.AssetDeserealizer

interface DeserializeAssetBundleManager : AssetBundleManager {

	fun addDeserializer(deserializer: AssetDeserealizer)

}
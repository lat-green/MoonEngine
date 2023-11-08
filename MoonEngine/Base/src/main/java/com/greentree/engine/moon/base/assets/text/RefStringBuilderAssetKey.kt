package com.greentree.engine.moon.base.assets.text

import com.greentree.engine.moon.assets.key.AssetKey

data class RefStringBuilderAssetKey(val text: AssetKey, val parameters: Map<String, String>) : AssetKey
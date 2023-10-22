package com.greentree.engine.moon.assets.location

import java.util.stream.Stream

interface NamedAssetLocation : AssetLocation {

	fun names(): Stream<String>
}
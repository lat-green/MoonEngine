package com.greentree.engine.moon.cooker

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo

interface AssetImporter {

	fun importAsset(ctx: Context, asset: AssetInfo): ImportAssetInfo?

	interface Context {

		fun ask(text: String): Boolean
	}
}

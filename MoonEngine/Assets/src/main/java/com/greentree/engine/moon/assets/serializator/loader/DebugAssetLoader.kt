package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey

class DebugAssetLoader(val loaders: AssetLoader) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey) = run {
		val ctx = DebugLoaderContext(context, type, key)
		val result = loaders.load(ctx, type, key)!!
		ctx.result = result
		ctx.end()
		if(ctx.time != null)
			println(ctx.xml())
		result
	}

	private inner class DebugLoaderContext(
		private val origin: AssetLoader.Context,
		private val type: TypeInfo<*>,
		private val key: AssetKey,
	) : AssetLoader.Context {

		private val children = mutableListOf<DebugLoaderContext>()
		private val start = System.currentTimeMillis()
		lateinit var result: Any
		var time = -1L

		override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> {
			val child = DebugLoaderContext(origin, type, key)
			val result = loaders.load(child, type, key)!!
			child.result = result
			child.end()
			children.add(child)
			return result
		}

		fun xml(): String {
			var text = ""
			text += "\n<type>$type</type>"
			text += "\n<key>$key</key>"
			if(::result.isInitialized)
				text += "\n<result>$result</result>"
			if(time != -1L) {
				text += "\n<time>$time</time>"
				text += "\n<seconds>${time / 1000f}</seconds>"
			}
			if(children.isNotEmpty())
				text += "\n<children>${
					children.map { "\n${it.xml()}" }.fold("") { a, b -> a + b }.upTab()
				}\n</children>"
			text = text.upTab()
			return "<node>$text\n</node>"
		}

		fun end() {
			val end = System.currentTimeMillis()
			time = end - start
		}
	}
}

private fun String.upTab() = this.replace("\n", "\n\t")

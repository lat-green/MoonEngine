package test.com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.context.AssetContext

class RunOnGetAssetProvider<T : Any>(
	private val origin: AssetProvider<T>,
	private val callback: (ctx: AssetContext) -> Unit,
) :
	AssetProvider<T> {

	constructor(
		origin: AssetProvider<T>,
		callback: Runnable,
	) : this(origin, { _ ->
		callback.run()
	})

	override fun value(ctx: AssetContext): T {
		callback(ctx)
		return origin.value(ctx)
	}

	override fun isConst() = origin.isConst()
	override fun isValid(ctx: AssetContext) = origin.isValid(ctx)

	override val lastModified: Long
		get() = origin.lastModified
}
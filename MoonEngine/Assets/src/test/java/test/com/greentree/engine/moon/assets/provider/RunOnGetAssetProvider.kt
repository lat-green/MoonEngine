package test.com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.request.AssetRequest

class RunOnGetAssetProvider<T : Any>(
	private val origin: AssetProvider<T>,
	private val callback: (ctx: AssetRequest) -> Unit,
) : AssetProvider<T> by origin {

	constructor(
		origin: AssetProvider<T>,
		callback: Runnable,
	) : this(origin, { _ ->
		callback.run()
	})

	override fun value(ctx: AssetRequest): T {
		callback(ctx)
		return origin.value(ctx)
	}
}
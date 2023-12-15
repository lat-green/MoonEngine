package com.greentree.engine.moon.assets.react

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.action.observer.run.RunAction
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.event.EventAsset
import com.greentree.engine.moon.assets.event.loader.EventAssetLoader
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.request.AssetRequest

class ReactAsset<T>(
	val origin: ReactAssetLoader,
	val assets: EventAssetLoader.Context,
	val type: TypeInfo<T>,
	val key: AssetKey,
) : EventAsset<T> {

	private val refs: MutableList<Pair<Ref<*>, () -> Unit>> = mutableListOf()
	private val action = RunAction()
	private lateinit var current: () -> Unit
	override val onChange: RunObservable
		get() = action

	private fun newCurrent(): () -> Unit {
		val callback = object : () -> Unit {
			override fun invoke() {
				if(current === this)
					action.event()
			}
		}
		current = callback
		return callback
	}

	init {
		origin.load(FirstReactContext(refs, newCurrent()), assets, type, key)
	}

	override fun value(request: AssetRequest): Result<T> = runCatching {
		origin.load(OtherReactContext(refs, newCurrent()), assets, type, key)
	}
}

private fun <T> ReactAssetLoader.load(
	ctx: ReactContext,
	assets: EventAssetLoader.Context,
	type: TypeInfo<T>,
	key: AssetKey,
) = load(object : ReactAssetLoader.Context, ReactContext by ctx {
	override val assets: EventAssetLoader.Context
		get() = assets
}, type, key)

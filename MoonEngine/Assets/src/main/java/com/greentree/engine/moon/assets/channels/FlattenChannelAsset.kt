package com.greentree.engine.moon.assets.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.isActive

class FlattenChannelAsset<T>(
	val source: ChannelAsset<ChannelAsset<T>>,
) : ChannelAsset<T> {

	@OptIn(ExperimentalCoroutinesApi::class)
	fun CoroutineScope.open() = produce {
		val source = source.open()
		var s = source.receive().open()
		while(isActive) {
			if(!source.isEmpty) {
				s.cancel()
				s = source.receive().open()
			}
			send(s.receive())
		}
	}

	override suspend fun open() = currentCoroutineScope { open() }
}
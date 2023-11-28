package com.greentree.engine.moon.assets.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.yield

class MutableChannelAsset<T>(var value: T) : ChannelAsset<T> {

	@OptIn(ExperimentalCoroutinesApi::class)
	fun CoroutineScope.open() = produce {
		while(isActive) {
			val v = value
			send(v)
			while(v == value)
				yield()
		}
	}

	override suspend fun open() = currentCoroutineScope { open() }
}
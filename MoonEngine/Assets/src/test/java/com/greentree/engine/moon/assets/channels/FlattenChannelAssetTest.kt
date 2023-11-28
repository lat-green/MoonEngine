package com.greentree.engine.moon.assets.channels

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FlattenChannelAssetTest {

	@Test
	fun test1() = runBlocking {
		val a = MutableChannelAsset("A")
		val b = MutableChannelAsset("B")
		val c = MutableChannelAsset(a)
		val f = FlattenChannelAsset(c)
		assertEquals(f.value(), "A")
		c.value = b
		assertEquals(f.value(), "B")
	}

	@Test
	fun test2() = runBlocking {
		val a = MutableChannelAsset("A")
		assertEquals(a.value(), "A")
		a.value = "B"
		assertEquals(a.value(), "B")
	}
}
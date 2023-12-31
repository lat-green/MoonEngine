package com.greentree.engine.moon.assets.bundle.manager

import java.io.InputStream

class LimitInputStream(
	private val origin: InputStream,
	private var limit: Int,
) : InputStream() {

	override fun read() =
		if(limit > 0)
			origin.read()
		else
			-1
}

fun InputStream.limit(length: Int) = LimitInputStream(this, length)
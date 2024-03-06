package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.util.iterator.IteratorUtil.*

private fun <T> Iterable<T>.isNotEmpty() = !isEmpty()
private fun <T> Iterable<T>.isEmpty() = isEmpty(this)
private val <T> Iterable<T>.size
	get() = size(this)
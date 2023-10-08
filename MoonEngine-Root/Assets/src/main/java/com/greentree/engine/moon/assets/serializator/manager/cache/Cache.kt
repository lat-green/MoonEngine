package com.greentree.engine.moon.assets.serializator.manager.cache

interface Cache<K, T> {

	operator fun get(key: K): T?
	operator fun set(key: K, create: () -> T): T
}
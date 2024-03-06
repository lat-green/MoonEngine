package com.greentree.engine.moon.assets.serializator.manager.cache

interface Cache<K, T> {

	operator fun get(key: K): T?
	operator fun <R : T> set(key: K, create: () -> R): R
}
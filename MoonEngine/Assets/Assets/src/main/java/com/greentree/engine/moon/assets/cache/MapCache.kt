package com.greentree.engine.moon.assets.cache

import java.util.concurrent.locks.StampedLock

class MapCache<K : Any, V>(private val map: MutableMap<K, V>) : Cache<K, V> {

	val locks = mutableMapOf<K, StampedLock>()

	override fun <R : V> set(key: K, create: () -> R): R {
//		val create = {
//			val timer = PointTimer()
//			timer.point()
//			try {
//				val result = create()
//				timer.point()
//				if(timer[0] > 1E-3)
//					println("$key ${timer[0]} $result")
//				result
//			} catch(e: Exception) {
//				timer.point()
//				if(timer[0] > 1E-3)
//					println("$key ${timer[0]} $e")
//				throw e
//			}
//		}
		val lock = synchronized(locks) {
			locks.getOrPut(key) { StampedLock() }
		}
		val stamp = lock.writeLock()
		try {
			return map.getOrPut(key, create) as R
		} finally {
			lock.unlockWrite(stamp)
		}
	}

	override fun get(key: K): V? {
		val lock = synchronized(locks) {
			locks.getOrPut(key) { StampedLock() }
		}
		val stamp = lock.readLock()
		try {
			return map[key]
		} finally {
			lock.unlockRead(stamp)
		}
	}
}
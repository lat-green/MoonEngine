package com.greentree.engine.moon.assets.serializator.manager.cache

import com.greentree.commons.util.time.PointTimer
import java.util.concurrent.locks.StampedLock

class MapCache<K : Any, V>(private val map: MutableMap<K, V>) : Cache<K, V> {

	val locks = mutableMapOf<K, StampedLock>()

	override fun set(key: K, create: () -> V): V {
		val lock = synchronized(locks) {
			locks.getOrPut(key) { StampedLock() }
		}
		val stamp = lock.writeLock()
		try {
			val timer = PointTimer()
			timer.point()
			try {
				val result = map.getOrPut(key, create)
				timer.point()
				if(timer[0] > 1E-3)
					println("$key ${timer[0]} $result")
				return result
			} catch(e: Exception) {
				timer.point()
				if(timer[0] > 1E-3)
					println("$key ${timer[0]} $e")
				throw e
			}
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
package com.greentree.engine.moon.ecs

open class ProxyPrototypeEntity(private val origin: PrototypeEntity) : PrototypeEntity by origin

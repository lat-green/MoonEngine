package com.greentree.engine.moon.ecs

open class ProxyPrototypeEntity(val origin: PrototypeEntity) : PrototypeEntity by origin

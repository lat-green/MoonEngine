package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo

class NoOneLoaderCanNotLoadType(type: TypeInfo<*>, key: Any, loaders: Iterable<*>) :
	RuntimeException("no one loader can\'t load type: $type, key: $key, loaders: $loaders")
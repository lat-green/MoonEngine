package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfoBuilder

abstract class TypedAssetSerializator<T : Any>() : AssetSerializator<T> {

	constructor(cls: Class<T>?) : this(TypeInfoBuilder.getTypeInfo<T>(cls))
}
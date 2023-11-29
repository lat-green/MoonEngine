package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.key.AssetKey

interface AssetComponentSerializator<T : Any> {

	val type: TypeInfo<T>
		get() = TypeUtil.getFirstAtgument(javaClass, AssetComponentSerializator::class.java)

	fun load(ctx: AssetComponentLoader.Context, key: AssetKey): T
}
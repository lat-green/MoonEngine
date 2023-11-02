package com.greentree.engine.moon.base.assets.scene

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader
import com.greentree.engine.moon.ecs.ClassSetEntity

object EntityDefault : DefaultLoader {

	override fun <T : Any> load(context: DefaultLoader.Context, type: TypeInfo<T>, key: AssetKeyType): T? {
		if(TypeUtil.isExtends(type, ClassSetEntity::class.java))
			return ClassSetEntity() as T
		return null
	}
}
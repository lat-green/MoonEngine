package com.greentree.engine.moon.assets.react

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyAndType
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.react.key.ReactKey
import com.greentree.engine.moon.assets.react.key.ResultReactKey

object ResultReactComponent : ReactComponent {

	override fun <T> value(
		ctx: ReactComponent.LoadContext,
		type: TypeInfo<T>,
		key: ReactKey,
	): ReactComponent.GetContext.() -> T {
		if(key !is ResultReactKey)
			throw NotSupportedKeyType
		val result = key.result
		if(!type.isInstance(result))
			throw NotSupportedKeyAndType
		return { result as T }
	}
}
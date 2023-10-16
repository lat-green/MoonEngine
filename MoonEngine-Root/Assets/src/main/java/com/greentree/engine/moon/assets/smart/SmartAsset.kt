package com.greentree.engine.moon.assets.smart

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.smart.info.AssetInfo

interface SmartAsset<T : Any> {

	fun info(context: Context): AssetInfo<T>

	interface Context {

		fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): AssetInfo<T>
	}

	fun openConnection(): Connection

	interface Connection {
		object Empty : Connection {

			override val isChange: Boolean
				get() = false
		}

		val isChange: Boolean
	}
}

inline fun <reified T : Any> SmartAsset.Context.load(key: AssetKey): AssetInfo<T> =
	load(getTypeInfo(T::class.java), key)
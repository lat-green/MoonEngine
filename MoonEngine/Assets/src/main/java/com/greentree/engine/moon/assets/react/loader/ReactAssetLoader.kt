package com.greentree.engine.moon.assets.react.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.react.ReactContext
import com.greentree.engine.moon.assets.react.useMemo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlin.reflect.KClass

interface ReactAssetLoader {

	suspend fun <T : Any> load(context: Context, type: TypeInfo<T>, key: AssetKey): T

	interface Context : ReactContext, CoroutineScope {

		suspend fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): T
	}
}

suspend fun <T : Any> ReactAssetLoader.Context.load(cls: Class<T>, key: AssetKey): T =
	load(getTypeInfo(cls), key)

suspend fun <T : Any> ReactAssetLoader.Context.load(cls: KClass<T>, key: AssetKey) = load(cls.java, key)
suspend inline fun <reified T : Any> ReactAssetLoader.Context.load(key: AssetKey) = load(T::class, key)

fun <R> ReactAssetLoader.Context.useAsync(
	dependency: Any = Unit,
	block: suspend CoroutineScope.() -> R,
): Deferred<R> =
	useMemo(dependency) {
		async(block = block)
	}


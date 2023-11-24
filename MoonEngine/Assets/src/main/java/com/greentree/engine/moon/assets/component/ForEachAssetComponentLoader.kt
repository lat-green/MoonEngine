package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil
import kotlin.reflect.KClass

class ForEachAssetComponentLoader<E, R> :
	AssetComponentLoader<ForEachKey<E, R>, Iterable<R>> {

	override fun load(ctx: AssetComponentContext, key: ForEachKey<E, R>) =
		ctx.load(key.elements).mapEach { key.func(it) }
			.mapEach(ctx.loadFunction(key.func.returnType))
			.strip()
}

val <T, R> ((T) -> R).returnType: TypeInfo<R>
	get() = TypeUtil.getTtype(this::class.type, Function1::class.java).typeArguments[1] as TypeInfo<R>
val <T : Any> KClass<T>.type
	get() = TypeInfoBuilder.getTypeInfo(this.java) as TypeInfo<T>

data class ForEachKey<E, R>(val elements: AssetComponentKey<Iterable<E>>, val func: (E) -> AssetComponentKey<R>) :
	AssetComponentKey<Iterable<R>>

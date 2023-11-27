package com.greentree.engine.moon.assets.provider.response

sealed interface AssetResponse<out T> {

	fun <R> map(block: (T) -> R): AssetResponse<R>
	fun <R> flatMap(block: (T) -> AssetResponse<R>): AssetResponse<R>
}

sealed interface ResultResponse<T> : AssetResponse<T> {

	val value: T
}

sealed interface NotValid<T> : AssetResponse<T> {

	override fun <R> map(block: (T) -> R) = this as AssetResponse<R>
	override fun <R> flatMap(block: (T) -> AssetResponse<R>) = this as AssetResponse<R>

}
sealed interface WithException<T> : NotValid<T> {

	val exception: Exception
}

sealed interface ConstResponse<T> : AssetResponse<T>

class ConstNotValid<T> : NotValid<T>, ConstResponse<T>
class BaseNotValid<T> : NotValid<T>

data class ConstNotValidWithException<T>(
	override val exception: Exception,
) : NotValid<T>, WithException<T>, ConstResponse<T>

data class BaseNotValidWithException<T>(
	override val exception: Exception,
) : NotValid<T>, WithException<T>

data class ConstResult<T>(
	override val value: T,
) : ResultResponse<T>, ConstResponse<T> {

	override fun <R> map(block: (T) -> R) = ConstResult(block(value))

	override fun <R> flatMap(block: (T) -> AssetResponse<R>) = block(value)
}

data class BaseResult<T>(
	override val value: T,
) : ResultResponse<T> {

	override fun <R> map(block: (T) -> R) = BaseResult(block(value))

	override fun <R> flatMap(block: (T) -> AssetResponse<R>) = when(val source = block(value)) {
		is ConstResult -> BaseResult(source.value)
		else -> source
	}
}


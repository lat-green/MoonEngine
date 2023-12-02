package com.greentree.engine.moon.assets.provider.response

sealed interface AssetResponse<out T> {

	val value: T

	fun toUpdatable(): UpdatableResponse<T>

	fun <R> map(block: (T) -> R): AssetResponse<R>
	fun <R> flatMap(block: (T) -> AssetResponse<R>): AssetResponse<R>
}

sealed interface ResultResponse<T> : AssetResponse<T> {

	override val value: T
}

sealed interface NotValid<out T> : AssetResponse<T> {

	override val value: T
		get() = throw UnsupportedOperationException()

	override fun <R> map(block: (T) -> R) = this as AssetResponse<R>
	override fun <R> flatMap(block: (T) -> AssetResponse<R>) = this as AssetResponse<R>
}

sealed interface WithException<out T> : NotValid<T> {

	override val value: T
		get() = throw exception
	val exception: Exception
}

sealed interface ConstResponse<out T> : AssetResponse<T>
sealed interface UpdatableResponse<out T> : AssetResponse<T> {

	override fun toUpdatable() = this
}

class ConstNotValid<T> : NotValid<T>, ConstResponse<T> {

	override fun toUpdatable() = BaseNotValid<T>()
}

class BaseNotValid<T> : NotValid<T>, UpdatableResponse<T>

data class ConstNotValidWithException<T>(
	override val exception: Exception,
) : NotValid<T>, WithException<T>, ConstResponse<T> {

	override fun toUpdatable() = BaseNotValidWithException<T>(exception)
}

data class BaseNotValidWithException<T>(
	override val exception: Exception,
) : NotValid<T>, WithException<T>, UpdatableResponse<T>

data class ConstResult<T>(
	override val value: T,
) : ResultResponse<T>, ConstResponse<T> {

	override fun toUpdatable() = BaseResult<T>(value)

	override fun <R> map(block: (T) -> R) =
		try {
			ConstResult(block(value))
		} catch(e: Exception) {
			ConstNotValidWithException(e)
		}

	override fun <R> flatMap(block: (T) -> AssetResponse<R>) =
		try {
			block(value)
		} catch(e: Exception) {
			ConstNotValidWithException(e)
		}
}

data class BaseResult<T>(
	override val value: T,
) : ResultResponse<T>, UpdatableResponse<T> {

	override fun <R> map(block: (T) -> R) =
		try {
			BaseResult(block(value))
		} catch(e: Exception) {
			BaseNotValidWithException(e)
		}

	override fun <R> flatMap(block: (T) -> AssetResponse<R>) =
		try {
			block(value).toUpdatable()
		} catch(e: Exception) {
			BaseNotValidWithException(e)
		}
}


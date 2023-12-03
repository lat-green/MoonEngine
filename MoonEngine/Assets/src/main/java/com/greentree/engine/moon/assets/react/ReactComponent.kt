package com.greentree.engine.moon.assets.react

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.react.key.ReactKey

interface ReactComponent {

	fun <T> value(ctx: LoadContext, type: TypeInfo<T>, key: ReactKey): GetContext.() -> T

	interface LoadContext {

		fun <T> useLoad(type: TypeInfo<T>, key: ReactKey): State<T>
		fun <T> useState(initValue: T): MutableState<T>
	}

	interface GetContext {

		fun <T> useLoad(type: TypeInfo<T>, key: ReactKey): T
		fun useEffect(block: () -> Unit, attachment: Any)
		fun <R> useMemo(block: () -> R, attachment: Any): R
		fun <R> useCallback(block: () -> R, attachment: Any): () -> R
	}
}


package com.greentree.engine.moon.assets.react.key

data class ResourceReactKey(
	val resourceName: ReactKey,
) : ReactKey {

	constructor(resourceName: String) : this(ResultReactKey(resourceName))
}
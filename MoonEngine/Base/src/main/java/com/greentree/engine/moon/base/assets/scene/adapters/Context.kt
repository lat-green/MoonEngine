package com.greentree.engine.moon.base.assets.scene.adapters

import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.xml.XMLElement

fun <T> findClass(baseClass: Class<T>, className: String): Class<T> {
	@Suppress("UNCHECKED_CAST")
	return ClassUtil.loadClassInAllPackages(baseClass, className) as Class<T>
}

interface Context {

	fun <T> build(cls: Class<T>, element: XMLElement): T {
		val type = TypeInfoBuilder.getTypeInfo(cls)
		return build(type, element)
	}

	fun <T> build(type: TypeInfo<T>, element: XMLElement): T {
		newInstance(type, element).use { c ->
			if(c == null) throw NullPointerException("type:$type element:$element")
			return c.value()
		}
	}

	fun <T> newInstance(type: TypeInfo<T>, element: XMLElement): Constructor<T>
	fun <T> newInstance(cls: Class<T>, element: XMLElement): Constructor<T> {
		val type = TypeInfoBuilder.getTypeInfo(cls)
		return newInstance(type, element)
	}
}
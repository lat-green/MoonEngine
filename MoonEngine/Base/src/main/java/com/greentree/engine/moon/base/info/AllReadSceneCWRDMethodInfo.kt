package com.greentree.engine.moon.base.info

import com.greentree.engine.moon.base.scene.SceneManagerProperty
import java.util.stream.Stream

class AllReadSceneCWRDMethodInfo(private val origin: CWRDMethodInfo) : CWRDMethodInfo {

	override fun getCreate(`object`: Any, method: String): Stream<out Class<*>> {
		return Stream.empty()
	}

	override fun getWrite(`object`: Any, method: String): Stream<out Class<*>> {
		return Stream.empty()
	}

	override fun getRead(obj: Any, method: String): Stream<out Class<*>> {
		return Stream.empty()
	}

	override fun getDestroy(`object`: Any, method: String): Stream<out Class<*>> {
		return Stream.empty()
	}

	override fun getPostDestroy(obj: Any, method: String): Stream<out Class<*>> {
		if("terminate" != method)
			return Stream.empty()
		val other = mutableListOf<Class<*>>()
		other.addAll(origin.getCreate(obj, method).toList())
		other.addAll(origin.getWrite(obj, method).toList())
		other.addAll(origin.getRead(obj, method).toList())
		other.addAll(origin.getDestroy(obj, method).toList())
		if(SceneManagerProperty::class.java in other)
			return Stream.of()
		return Stream.of(SceneManagerProperty::class.java)
	}
}

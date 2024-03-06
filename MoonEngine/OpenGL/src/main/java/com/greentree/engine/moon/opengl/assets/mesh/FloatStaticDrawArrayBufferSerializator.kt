package com.greentree.engine.moon.opengl.assets.mesh

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

data object FloatStaticDrawArrayBufferSerializator : AssetSerializator<FloatStaticDrawArrayBuffer> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): FloatStaticDrawArrayBuffer {
		val array = manager.load<FloatArray>(key)
		val vbo = FloatStaticDrawArrayBuffer()
		vbo.bind()
		vbo.setData(array)
		vbo.unbind()
		return vbo
	}
}
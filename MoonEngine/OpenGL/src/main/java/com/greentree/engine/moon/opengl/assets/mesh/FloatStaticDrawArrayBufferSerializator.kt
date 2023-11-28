package com.greentree.engine.moon.opengl.assets.mesh

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load

class FloatStaticDrawArrayBufferSerializator : AssetSerializator<FloatStaticDrawArrayBuffer> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<FloatStaticDrawArrayBuffer> {
		val vao = manager.load<FloatArray>(key)
		return vao.map(AttributeDataToGLVertexArray)
	}

	object AttributeDataToGLVertexArray : Value1Function<FloatArray, FloatStaticDrawArrayBuffer> {

		override fun apply(attribute: FloatArray): FloatStaticDrawArrayBuffer {
			return applyWithDest(attribute, FloatStaticDrawArrayBuffer())
		}

		override fun applyWithDest(vertexes: FloatArray, vbo: FloatStaticDrawArrayBuffer): FloatStaticDrawArrayBuffer {
			vbo.bind()
			vbo.setData(vertexes)
			vbo.unbind()
			return vbo
		}
	}
}
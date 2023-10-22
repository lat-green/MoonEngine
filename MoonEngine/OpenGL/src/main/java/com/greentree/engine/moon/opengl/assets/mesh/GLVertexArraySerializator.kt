package com.greentree.engine.moon.opengl.assets.mesh

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer
import com.greentree.common.graphics.sgl.vao.GLVertexArray
import com.greentree.common.graphics.sgl.vao.GLVertexArray.*
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.mesh.AttributeData

class GLVertexArraySerializator : AssetSerializator<GLVertexArray> {

	override fun load(manager: AssetManager, key: AssetKey): Asset<GLVertexArray> {
		val vao = manager.load<AttributeData>(key)
		return vao.map(AttributeDataToGLVertexArray)
	}

	object AttributeDataToGLVertexArray : Value1Function<AttributeData, GLVertexArray> {

		override fun apply(attribute: AttributeData): GLVertexArray {
			val vbo = getVBO(attribute.vertex())
			var offset = 0
			val stride = attribute.sizes().sum() * vbo.dataType.size
			val vao = GLVertexArray(attribute.sizes().mapIndexed { location, size ->
				val result = GLVertexArray.AttributeGroup(location, vbo, size, stride, offset, 0)
				offset += size * vbo.dataType.size
				result
			})
			return vao
		}

		private fun getVBO(vertexes: FloatArray): FloatStaticDrawArrayBuffer {
			val vbo = FloatStaticDrawArrayBuffer()
			vbo.bind()
			vbo.setData(vertexes)
			vbo.unbind()
			return vbo
		}
	}
}
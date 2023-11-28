package com.greentree.engine.moon.opengl.assets.mesh

import com.greentree.common.graphics.sgl.vao.GLVertexArray
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.opengl.adapter.VAORenderMeshAdapter

class VAORenderMeshAdapterSerializator : AssetSerializator<VAORenderMeshAdapter> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<VAORenderMeshAdapter> {
		val vao = manager.load<GLVertexArray>(key)
		return vao.map(VAO)
	}

	object VAO : Value1Function<GLVertexArray, VAORenderMeshAdapter> {

		override fun apply(value: GLVertexArray): VAORenderMeshAdapter {
			return VAORenderMeshAdapter(value)
		}
	}
}
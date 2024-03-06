package com.greentree.engine.moon.opengl.assets.mesh

import com.greentree.common.graphics.sgl.vao.GLVertexArray
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.opengl.adapter.VAORenderMeshAdapter

data object VAORenderMeshAdapterSerializator : AssetSerializator<VAORenderMeshAdapter> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): VAORenderMeshAdapter {
		val vao = manager.load<GLVertexArray>(key)
		return VAORenderMeshAdapter(vao)
	}
}
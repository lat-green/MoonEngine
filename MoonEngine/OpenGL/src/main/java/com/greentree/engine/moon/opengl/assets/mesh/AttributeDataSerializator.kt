package com.greentree.engine.moon.opengl.assets.mesh

import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.mesh.AttributeData
import com.greentree.engine.moon.mesh.StaticMesh
import com.greentree.engine.moon.opengl.adapter.GLRenderContext

data object AttributeDataSerializator : AssetSerializator<AttributeData> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AttributeData {
		val mesh = manager.load<StaticMesh>(key)
		return mesh.getAttributeGroup(*GLRenderContext.COMPONENTS)
	}
}
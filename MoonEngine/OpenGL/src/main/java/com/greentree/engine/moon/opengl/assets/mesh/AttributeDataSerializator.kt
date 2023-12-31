package com.greentree.engine.moon.opengl.assets.mesh

import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.mesh.AttributeData
import com.greentree.engine.moon.mesh.StaticMesh
import com.greentree.engine.moon.opengl.adapter.GLRenderContext

object AttributeDataSerializator : AssetSerializator<AttributeData> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<AttributeData> {
		val mesh = manager.load<StaticMesh>(key)
		return mesh.map(AttributeDataToGLVertexArray)
	}

	object AttributeDataToGLVertexArray : Value1Function<StaticMesh, AttributeData> {

		override fun apply(mesh: StaticMesh): AttributeData {
			return mesh.getAttributeGroup(*GLRenderContext.COMPONENTS)
		}
	}
}
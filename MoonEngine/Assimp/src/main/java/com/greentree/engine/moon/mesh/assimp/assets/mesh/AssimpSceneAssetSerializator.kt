package com.greentree.engine.moon.mesh.assimp.assets.mesh

import com.greentree.commons.data.resource.Resource
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.mesh.assimp.AssimpScene
import org.lwjgl.assimp.Assimp
import org.lwjgl.system.MemoryUtil
import java.io.IOException
import java.nio.ByteBuffer

data object AssimpSceneAssetSerializator : AssetSerializator<AssimpScene> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssimpScene {
		val resource = manager.load<Resource>(key)
		var _data: ByteArray
		var data: ByteBuffer
		try {
			resource.open().use { `in` ->
				_data = `in`.readAllBytes()
				data = MemoryUtil.memCalloc(_data.size)
				data.put(_data)
				data.flip()
			}
		} catch(e: IOException) {
			throw IllegalArgumentException(e)
		}
		try {
			Assimp.aiImportFileFromMemory(
				data, Assimp.aiProcess_Triangulate
						or Assimp.aiProcess_ValidateDataStructure or Assimp.aiProcess_OptimizeMeshes, ""
			).use { scene ->
				if(scene == null) {
					val error = Assimp.aiGetErrorString()
					throw IllegalArgumentException(error)
				}
				val s: AssimpScene =
					AssimpScene(scene, AssimpScene.NOT_LOAD_MATERIAL or AssimpScene.NOT_LOAD_TEXTURES)
				return s
			}
		} finally {
			MemoryUtil.memFree(data)
		}
	}
}

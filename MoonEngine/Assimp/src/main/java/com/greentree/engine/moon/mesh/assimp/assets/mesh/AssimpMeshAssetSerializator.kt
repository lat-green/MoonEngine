package com.greentree.engine.moon.mesh.assimp.assets.mesh

import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.mesh.GraphicsMesh
import com.greentree.engine.moon.mesh.StaticMesh.*
import com.greentree.engine.moon.mesh.assimp.AssimpScene

data object AssimpMeshAssetSerializator : AssetSerializator<GraphicsMesh> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): GraphicsMesh {
		val scene = manager.load(AssimpScene::class.java, key)
		val mesh = scene.mMeshes(0)
		val builder = GraphicsMesh.builder()
		for(f in 0 until mesh.mNumFaces()) {
			val face = mesh.mFaces(f)
			var ind = 0
			while(ind < face.mNumIndices()) {
				val ai = face.mIndices(ind)
				val bi = face.mIndices(ind + 1)
				val ci = face.mIndices(ind + 2)
				val av = mesh.mVertices(ai)
				val bv = mesh.mVertices(bi)
				val cv = mesh.mVertices(ci)
				val an = mesh.mNormals(ai)
				val bn = mesh.mNormals(bi)
				val cn = mesh.mNormals(ci)
				val at = mesh.mTextureCoords(0, ai)
				val bt = mesh.mTextureCoords(0, bi)
				val ct = mesh.mTextureCoords(0, ci)
				val a = ValueVertexIndex(av, an, at)
				val b = ValueVertexIndex(bv, bn, bt)
				val c = ValueVertexIndex(cv, cn, ct)
				builder.addFaces(a, b, c)
				ind += 3
			}
		}
		return builder.build()
	}
}

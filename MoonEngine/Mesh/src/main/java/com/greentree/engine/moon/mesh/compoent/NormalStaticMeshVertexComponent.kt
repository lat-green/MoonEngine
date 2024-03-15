package com.greentree.engine.moon.mesh.compoent

import com.greentree.engine.moon.mesh.StaticMesh
import com.greentree.engine.moon.mesh.StaticMesh.*

data object NormalStaticMeshVertexComponent : StaticMeshVertexComponent {

	override fun get(face: MeshFace, strip: Int, a: FloatArray, b: FloatArray, c: FloatArray) {
		if(face.hasNormals()) super.get(face, strip, a, b, c) else {
			val va = face.a.vertex()
			val vb = face.b.vertex()
			val vc = face.c.vertex()
			val normal = (va - vb).cross(vc - vb)
				.normalize(-1)
			StaticMeshFaceComponent.setVector(a, strip, normal)
			StaticMeshFaceComponent.setVector(b, strip, normal)
			StaticMeshFaceComponent.setVector(c, strip, normal)
		}
	}

	override fun get(index: VertexIndex, strip: Int, dest: FloatArray) {
		if(!index.hasNormal()) throw UnsupportedOperationException()
		StaticMeshFaceComponent.setVector(dest, strip, index.normal())
	}

	override fun size(): Int {
		return 3
	}
}
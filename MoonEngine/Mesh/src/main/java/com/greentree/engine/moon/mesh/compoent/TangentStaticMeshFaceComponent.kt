package com.greentree.engine.moon.mesh.compoent

import com.greentree.commons.math.vector.Vector2f
import com.greentree.commons.math.vector.Vector3f
import com.greentree.engine.moon.mesh.StaticMesh.*

class TangentStaticMeshFaceComponent private constructor() : StaticMeshFaceComponent {

	override fun size(): Int {
		return 3
	}

	override fun get(face: MeshFace, strip: Int, a: FloatArray, b: FloatArray, c: FloatArray) {
		val av = face.a.vertex()
		val bv = face.b.vertex()
		val cv = face.c.vertex()
		val tex_a = FloatArray(2)
		val tex_b = FloatArray(2)
		val tex_c = FloatArray(2)
		MeshComponent.TEXTURE_COORDINAT[face, 0, tex_a, tex_b, tex_c]
		val at = Vector2f(tex_a[0], tex_a[1])
		val bt = Vector2f(tex_b[0], tex_b[1])
		val ct = Vector2f(tex_c[0], tex_c[1])
		val arc1 = bv - av
		val arc2 = cv.minus(av)
		val deltaUV1 = bt.minus(at)
		val deltaUV2 = ct.minus(at)
		val f = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y)
		val tangent = Vector3f()
		tangent.x = f * (deltaUV2.y * arc1.x - deltaUV1.y * arc2.x)
		tangent.y = f * (deltaUV2.y * arc1.y - deltaUV1.y * arc2.y)
		tangent.z = f * (deltaUV2.y * arc1.z - deltaUV1.y * arc2.z)
		tangent.normalize()
		StaticMeshFaceComponent.setVector(a, strip, tangent)
		StaticMeshFaceComponent.setVector(b, strip, tangent)
		StaticMeshFaceComponent.setVector(c, strip, tangent)
	}

	companion object {

		@JvmField
		val INSTANCE = TangentStaticMeshFaceComponent()
	}
}